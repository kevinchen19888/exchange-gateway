package com.alchemy.gateway.exchangeclients.bitfinex;

import com.alchemy.gateway.core.GatewayException;
import com.alchemy.gateway.core.common.CandleInterval;
import com.alchemy.gateway.core.common.CoinPairSymbolConverter;
import com.alchemy.gateway.core.common.Market;
import com.alchemy.gateway.core.marketdata.CandleTick;
import com.alchemy.gateway.core.marketdata.DataSubscription;
import com.alchemy.gateway.core.marketdata.MarketDataApi;
import com.alchemy.gateway.core.marketdata.MarketDataReceiver;
import com.alchemy.gateway.core.utils.DateUtils;
import com.alchemy.gateway.exchangeclients.bitfinex.websocket.BitfinexTextWebsocketHandler;
import com.alchemy.gateway.exchangeclients.bitfinex.websocket.WsSubscribeObj;
import com.alchemy.gateway.exchangeclients.common.HttpUtil;
import com.alibaba.fastjson.JSONArray;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;


@Slf4j
public class BitfinexMarketDataApi implements MarketDataApi {
    private final BitfinexExchangeApi exchangeApi;

    private static final Pattern TIMESTAMP_REG = Pattern.compile("^[0-9]{1,20}$");

    private final RateLimiter rateLimiter = RateLimiter.create(1);


    public BitfinexMarketDataApi(BitfinexExchangeApi exchangeApi) {
        this.exchangeApi = exchangeApi;
    }

    @Override
    public DataSubscription subscribe(Market market, @Nullable List<CandleInterval> intervals, boolean trades, boolean orderBook,
                                      boolean tricks24h, MarketDataReceiver dataReceiver) {
        String symbol = exchangeApi.getCoinPairSymbolConverter().coinPairToSymbol(market.getCoinPair());

        List<String> subList = assembleTopics(intervals, trades, orderBook, tricks24h, symbol);

        try {
            exchangeApi.connectWebSocket(new BitfinexTextWebsocketHandler(dataReceiver, subList));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Bitfinex websocket连接进行消息订阅出现异常,symbol:{},e:{}", symbol, e.toString());
        }

        return null;
    }


    /**
     * 1m: one minute
     * 5m : five minutes
     * 15m : 15 minutes
     * 30m : 30 minutes
     * 1h : one hour
     * 3h : 3 hours
     * 6h : 6 hours
     * 12h : 12 hours
     * 1D : one day
     * 7D : one week
     * 14D : two weeks
     * 1M : one month
     *
     */
    @Override
    public List<CandleInterval> supportCandleIntervals() {
        return Arrays.asList(
                CandleInterval.MONTH_1_M,
                CandleInterval.DAY_14,
                CandleInterval.DAY_7_D,
                CandleInterval.DAY_1_D,
                CandleInterval.HOUR_12,
                CandleInterval.HOUR_6,
                CandleInterval.HOUR_3,
                CandleInterval.HOUR_1,
                CandleInterval.MINUTES_30,
                CandleInterval.MINUTES_15,
                CandleInterval.MINUTES_5,
                CandleInterval.MINUTES_1
        );
    }

    @Override
    public long getMaxTickCountPerRequest() {
        return 1000;
    }

    @Override
    public List<CandleTick> getHistory(Market market, CandleInterval interval, LocalDateTime start, LocalDateTime end) {

        if (!supportsCandleInterval(interval)) {
            throw new IllegalArgumentException("不支持的K线间隔,interval:" + interval);
        }

        rateLimiter.acquire();

        List<CandleTick> klines = new ArrayList<>();

        // 设置标志位 start 和end 是否无效    如果 start&end都不合法,则直接返回空list
        int startFlag=1;
        int endFlag=1;


        CoinPairSymbolConverter coinPairConverter = exchangeApi.getCoinPairSymbolConverter();
        String symbol = coinPairConverter.coinPairToSymbol(market.getCoinPair());

        // candles/trade:1m:tBTCUSD/hist
        StringBuilder uri = new StringBuilder("candles/trade:" + interval.getSymbol() + ":" + symbol + "/hist?limit=" + getMaxTickCountPerRequest());

        if (start != null ) {

            if (start.isAfter(LocalDateTime.now()))
            {
                start=LocalDateTime.now();
                startFlag=0;
            }

            Long startTimeStamp = start.toInstant(ZoneOffset.of("+8")).toEpochMilli();
            if (TIMESTAMP_REG.matcher(String.valueOf(startTimeStamp)).find())
            {
                uri.append("&start=").append(startTimeStamp);
            }

        }
        if (end != null) {
            if (end.isAfter(LocalDateTime.now()))
            {
                end=LocalDateTime.now();
                endFlag=0;
            }
            long endTimeStamp = end.toInstant(ZoneOffset.of("+8")).toEpochMilli();
            if (!TIMESTAMP_REG.matcher(String.valueOf(endTimeStamp)).find()) {
                endTimeStamp = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
            }
            uri.append("&end=").append(endTimeStamp);
        }

        RestTemplate restTemplate = exchangeApi.getRestTemplate();


        if (startFlag==0 && endFlag==0)
        {
            return  klines;
        }

        ResponseEntity<String> exchangeResp = restTemplate.getForEntity(BitfinexExchangeApi.RESTFUL_QUATATION
                + uri.toString(), String.class);

        if (HttpUtil.isSuccessResp(exchangeResp)) {
            JSONArray klineArray = JSONArray.parseArray(exchangeResp.getBody());

            if (klineArray != null) {
                for (int i = 0; i < klineArray.size(); i++) {
                    JSONArray kline = klineArray.getJSONArray(i);

                    CandleTick candle = new CandleTick();
                    candle.setTimeStamp(DateUtils.getEpochMilliByTime(kline.getLong(0)));
                    candle.setHigh(kline.getBigDecimal(3));
                    candle.setOpen(kline.getBigDecimal(1));
                    candle.setLow(kline.getBigDecimal(4));
                    candle.setClose(kline.getBigDecimal(2));
                    candle.setVolume(kline.getBigDecimal(5));


                    klines.add(candle);
                }
            }

            return klines;
        } else {
            log.warn("bitfinex 获取指定市场的历史数据-market:{},interval:{},statusCode:{}", market.getCoinPair(), interval, exchangeResp.getStatusCode());
            throw new GatewayException("bitfinex 获取指定市场的历史数据失败,statusCode:" + exchangeResp.getStatusCode());
        }
    }

    private List<String> assembleTopics(@Nullable List<CandleInterval> intervals, boolean trades, boolean orderBook, boolean tricks24h, String symbol) {
        // 组装k线主题

        List<String> subList = new ArrayList<>();

        WsSubscribeObj subObj;

        if (orderBook) {
            // p0  p2 p4
            subObj = WsSubscribeObj.builder().event("subscribe").channel("book").symbol(symbol).prec("P0").build();
            subList.add(subObj.toJson());

            subObj = WsSubscribeObj.builder().event("subscribe").channel("book").symbol(symbol).prec("P2").build();
            subList.add(subObj.toJson());

            subObj = WsSubscribeObj.builder().event("subscribe").channel("book").symbol(symbol).prec("P4").build();
            subList.add(subObj.toJson());

        }
        if (intervals==null)
        {
           //  intervals=supportCandleIntervals();
        }
        if (intervals != null && intervals.size() > 0) {
            for (CandleInterval inv : intervals) {
                subObj = WsSubscribeObj.builder().event("subscribe").channel("candles").key("trade:" + inv.getSymbol() + ":" + symbol).build();
                subList.add(subObj.toJson());
            }

        }
        if (trades) {
            subObj = WsSubscribeObj.builder().event("subscribe").channel("trades").symbol(symbol).build();
            subList.add(subObj.toJson());

        }

        if (tricks24h) {
            subObj = WsSubscribeObj.builder().event("subscribe").channel("ticker").symbol(symbol).build();
            subList.add(subObj.toJson());
        }

        return subList;
    }
}
