package com.alchemy.gateway.exchangeclients.binance;

import com.alchemy.gateway.core.GatewayException;
import com.alchemy.gateway.core.common.CandleInterval;
import com.alchemy.gateway.core.common.CoinPairSymbolConverter;
import com.alchemy.gateway.core.common.Market;
import com.alchemy.gateway.core.marketdata.CandleTick;
import com.alchemy.gateway.core.marketdata.DataSubscription;
import com.alchemy.gateway.core.marketdata.MarketDataApi;
import com.alchemy.gateway.core.marketdata.MarketDataReceiver;
import com.alchemy.gateway.exchangeclients.binance.websocket.BinanceTextWebsocketHandler;
import com.alchemy.gateway.exchangeclients.common.HttpUtil;
import com.alibaba.fastjson.JSONArray;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;

import java.net.InetAddress;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;

@Slf4j
public class BinanceMarketDataApi implements MarketDataApi {
    private final BinanceExchangeApi exchangeApi;
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final Pattern TIMESTAMP_REG = Pattern.compile("^[0-9]{1,20}$");

    public BinanceMarketDataApi(BinanceExchangeApi exchangeApi) {
        this.exchangeApi = exchangeApi;
    }

    @Override
    public DataSubscription subscribe(Market market, @Nullable List<CandleInterval> intervals, boolean trades, boolean orderBook,
                                      boolean tricks24h, MarketDataReceiver dataReceiver) {
        log.info("开始订阅 binance 行情数据================>>>");
        if (market == null || market.getCoinPair() == null) {
            throw new IllegalArgumentException("binance 行情数据订阅未指定市场-market:" + market);
        }
        String symbol = exchangeApi.getCoinPairSymbolConverter().coinPairToSymbol(market.getCoinPair()).toLowerCase();

        intervals = intervals == null ? supportCandleIntervals() : intervals;

        List<String> subscribeList = assembleTopics(intervals, trades, orderBook, tricks24h, symbol);

        try {
            // 拼接处理websocket连接url
            String socketUrl = exchangeApi.getConnectionInfo().getWebSocketEndpoint() + "/stream?streams=";
            String streams = String.join("/", subscribeList);
            exchangeApi.connectWebSocket(new BinanceTextWebsocketHandler(dataReceiver), socketUrl + streams);
        } catch (Exception e) {
            log.error("binance websocket连接进行消息订阅出现异常,symbol:{},e:{}", symbol, e.toString());
        }

        return null;
    }

    @Override
    public List<CandleInterval> supportCandleIntervals() {
        return Arrays.asList(
                CandleInterval.MONTH_1_M,
                CandleInterval.WEEK_1,
                CandleInterval.DAY_3,
                CandleInterval.DAY_1,
                CandleInterval.HOUR_12,
                CandleInterval.HOUR_8,
                CandleInterval.HOUR_6,
                CandleInterval.HOUR_4,
                CandleInterval.HOUR_2,
                CandleInterval.HOUR_1,
                CandleInterval.MINUTES_30,
                CandleInterval.MINUTES_15,
                CandleInterval.MINUTES_5,
                CandleInterval.MINUTES_3,
                CandleInterval.MINUTES_1
        );
    }

    @Override
    public long getMaxTickCountPerRequest() {
        return 1000;
    }

    @Override
    @SneakyThrows
    public List<CandleTick> getHistory(Market market, CandleInterval interval, LocalDateTime start, LocalDateTime end) {
        exchangeApi.getRateLimiterManager().getRateLimiter(InetAddress.getLocalHost().getHostAddress(),
                10).acquire();

        if (!supportsCandleInterval(interval)) {
            throw new IllegalArgumentException("不支持的K线间隔,interval:" + interval);
        }
        List<CandleTick> klines = new ArrayList<>();
        // 如果 start&end都不合法,则直接返回空list
        if (start != null && end != null) {
            String startTimeStr = String.valueOf(start.toInstant(ZoneOffset.of("+8")).toEpochMilli());
            String endTimeStr = String.valueOf(end.toInstant(ZoneOffset.of("+8")).toEpochMilli());
            if (!TIMESTAMP_REG.matcher(startTimeStr).find() && !TIMESTAMP_REG.matcher(endTimeStr).find()) {
                return klines;
            }
        }

        CoinPairSymbolConverter coinPairConverter = exchangeApi.getCoinPairSymbolConverter();
        String symbol = coinPairConverter.coinPairToSymbol(market.getCoinPair());

        StringBuilder uri = new StringBuilder("?symbol=" + symbol + "&interval=" + interval.getSymbol() + "&limit=" + getMaxTickCountPerRequest());
        if (start != null) {
            long startTimeStamp = start.toInstant(ZoneOffset.of("+8")).toEpochMilli();
            // 如果不符合binance开始时间格式要求,则默认传0(1970年时间)
            if (!TIMESTAMP_REG.matcher(String.valueOf(startTimeStamp)).find()) {
                startTimeStamp = 0;
            }
            uri.append("&startTime=").append(startTimeStamp);
        }
        if (end != null) {
            long endTimeStamp = end.toInstant(ZoneOffset.of("+8")).toEpochMilli();
            if (!TIMESTAMP_REG.matcher(String.valueOf(endTimeStamp)).find()) {
                endTimeStamp = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
            }
            uri.append("&endTime=").append(endTimeStamp);
        }

        ResponseEntity<String> exchangeResp = exchangeApi.getRestTemplate()
                .getForEntity(exchangeApi.getConnectionInfo().getRestfulApiEndpoint()
                        + "/api/v3/klines" + uri.toString(), String.class);

        if (HttpUtil.isSuccessResp(exchangeResp)) {
            JSONArray klineArray = JSONArray.parseArray(exchangeResp.getBody());
            if (klineArray != null) {
                for (int i = 0; i < klineArray.size(); i++) {
                    CandleTick candleTick = new CandleTick();
                    JSONArray kline = klineArray.getJSONArray(i);

                    candleTick.setTimeStamp(LocalDateTime.ofEpochSecond(kline.getLongValue(0) / 1000,
                            0, ZoneOffset.ofHours(8)));
                    candleTick.setOpen(kline.getBigDecimal(1));
                    candleTick.setHigh(kline.getBigDecimal(2));
                    candleTick.setLow(kline.getBigDecimal(3));
                    candleTick.setClose(kline.getBigDecimal(4));
                    candleTick.setVolume(kline.getBigDecimal(5));

                    Map<String, String> reserveMap = new HashMap<>();
                    LocalDateTime closeTime = LocalDateTime.ofEpochSecond(kline.getLongValue(6) / 1000,
                            0, ZoneOffset.ofHours(8));
                    reserveMap.put("close_time", TIME_FORMATTER.format(closeTime));
                    reserveMap.put("count", kline.getString(8));
                    reserveMap.put("bid_volume", kline.getString(9));
                    reserveMap.put("bid_amount", kline.getString(10));
                    candleTick.setReserveMap(reserveMap);

                    klines.add(candleTick);
                }
            }
            return klines;
        } else {
            log.warn("binance获取指定市场的历史数据-market:{},interval:{},statusCode:{}", market.getCoinPair(), interval, exchangeResp.getStatusCode());
            throw new GatewayException("binance获取指定市场的历史数据失败,statusCode:" + exchangeResp.getStatusCode());
        }
    }

    private List<String> assembleTopics(@Nullable List<CandleInterval> intervals, boolean trades, boolean orderBook, boolean tricks24h, String symbol) {
        // 组装k线主题
        List<String> subscribeList = new ArrayList<>();
        if (intervals != null) {
            for (CandleInterval interval : intervals) {
                if (supportsCandleInterval(interval)) {
                    subscribeList.add(String.format("%s@kline_%s", symbol, interval.getSymbol()));
                } else {
                    log.warn("不支持的kline周期:{}", interval);
                }
            }
        }
        // 如果订阅最新成交
        if (trades) {
            subscribeList.add(symbol + "@trade");
        }
        // 如果订阅深度数据
        if (orderBook) {
            subscribeList.add(symbol + "@depth10");
        }
        // 如果24小时ticker数据
        if (tricks24h) {
            subscribeList.add(symbol + "@miniTicker");
        }
        return subscribeList;
    }
}
