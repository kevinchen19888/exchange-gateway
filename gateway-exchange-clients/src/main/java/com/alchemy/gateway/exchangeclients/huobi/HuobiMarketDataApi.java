package com.alchemy.gateway.exchangeclients.huobi;

import com.alchemy.gateway.core.AbstractExchangeApi;
import com.alchemy.gateway.core.GatewayException;
import com.alchemy.gateway.core.common.CandleInterval;
import com.alchemy.gateway.core.common.CoinPairSymbolConverter;
import com.alchemy.gateway.core.common.Market;
import com.alchemy.gateway.core.common.RateLimiterManager;
import com.alchemy.gateway.core.marketdata.CandleTick;
import com.alchemy.gateway.core.marketdata.DataSubscription;
import com.alchemy.gateway.core.marketdata.MarketDataApi;
import com.alchemy.gateway.core.marketdata.MarketDataReceiver;
import com.alchemy.gateway.core.utils.DateUtils;
import com.alchemy.gateway.core.websocket.BinaryMessageCompression;
import com.alchemy.gateway.core.websocket.CompressionWebSocketHandler;
import com.alchemy.gateway.exchangeclients.common.HttpUtil;
import com.alchemy.gateway.exchangeclients.huobi.websocket.HuobiBinaryMessageCompression;
import com.alchemy.gateway.exchangeclients.huobi.websocket.HuobiWebsocketHandler;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.util.concurrent.RateLimiter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.ConnectionManagerSupport;

import java.io.IOException;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author kevin chen
 */
@Slf4j
public class HuobiMarketDataApi implements MarketDataApi {

    private final AbstractExchangeApi exchangeApi;
    private final BinaryMessageCompression compression;
    private ObjectMapper objectMapper = new ObjectMapper();

    public HuobiMarketDataApi(AbstractExchangeApi exchangeApi) {
        this.exchangeApi = exchangeApi;
        compression = new HuobiBinaryMessageCompression();
    }

    @Override
    public DataSubscription subscribe(Market market, List<CandleInterval> intervals, boolean trades, boolean orderBook,
                                      boolean tricks24h, MarketDataReceiver dataReceiver) {
        log.info("开始订阅 huobi 行情数据==========>>>");
        if (market == null || market.getCoinPair() == null) {
            throw new IllegalArgumentException("huobi行情数据订阅未指定市场-market:" + market);
        }
        intervals = intervals == null ? supportCandleIntervals() : intervals;

        List<String> subscribeList = addSubscription(market, intervals, trades, orderBook, tricks24h);

        try {
            exchangeApi.connectWebSocket(new HuobiWebsocketHandler(compression, subscribeList, dataReceiver));
        } catch (Exception e) {
            log.error("huobi websocket订阅行情数据失败-e:{}", e.toString());
        }
        return null;
    }

    @Override
    public List<CandleInterval> supportCandleIntervals() {
        return Arrays.asList(
                CandleInterval.YEAR_1_YEAR,
                CandleInterval.MONTH_1MON,
                CandleInterval.WEEK_1_1WEEK,
                CandleInterval.DAY_1DAY,
                CandleInterval.HOUR_4HOUR,
                CandleInterval.MINUTES_60,
                CandleInterval.MINUTES_30MIN,
                CandleInterval.MINUTES_15MIN,
                CandleInterval.MINUTES_5MIN,
                CandleInterval.MINUTES_1MIN
        );
    }

    @Override
    public long getMaxTickCountPerRequest() {
        return 2000;
    }

    @SneakyThrows
    @Override
    public List<CandleTick> getHistory(Market market, CandleInterval interval, LocalDateTime start, LocalDateTime end) {
        RateLimiterManager limiterManager = exchangeApi.getRateLimiterManager();
        RateLimiter limiter = limiterManager.getRateLimiter(InetAddress.getLocalHost().getHostAddress(), 10);
        limiter.acquire();

        if (!supportsCandleInterval(interval)) {
            throw new IllegalArgumentException("不支持的huobi K线间隔,interval:" + interval);
        }

        CoinPairSymbolConverter coinPairConverter = exchangeApi.getCoinPairSymbolConverter();
        String symbol = coinPairConverter.coinPairToSymbol(market.getCoinPair());

        String subscription = String.format("market.%s.kline.%s", symbol, interval.getSymbol());

        HuobiHistoryKlineHandler handler = new HuobiHistoryKlineHandler(compression, subscription,
                start.toEpochSecond(ZoneOffset.of("+8")),
                end.toEpochSecond(ZoneOffset.of("+8")));

        ConnectionManagerSupport webSocket = exchangeApi.connectWebSocket(handler);

        webSocket.stop();


        // todo huobi需要通过websocket方式获取历史kline数据
        //exchangeApi.getWebSocketClient()

        //final String url = exchangeApi.getConnectionInfo().getRestfulApiEndpoint() + "/market/history/kline"
        //        + "?symbol=" + symbol + "&period=" + interval.getSymbol() + "&size=" + getMaxTickCountPerRequest();
        //
        //HttpHeaders headers = new HttpHeaders();
        //headers.add("user-agent", HttpUtil.USER_AGENT);
        //HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        //
        //ResponseEntity<String> exchangeResp = exchangeApi.getRestTemplate().exchange(url, HttpMethod.GET, httpEntity, String.class);
        //List<CandleTick> ticks = new ArrayList<>();
        //if (HttpUtil.isSuccessResp(exchangeResp)) {
        //    JSONArray tickArr = JSON.parseObject(exchangeResp.getBody()).getJSONArray("data");
        //    assert tickArr != null;
        //    for (int i = 0; i < tickArr.size(); i++) {
        //        CandleTick tick = new CandleTick();
        //        tick.setOpen(tickArr.getJSONObject(i).getBigDecimal("open"));
        //        tick.setClose(tickArr.getJSONObject(i).getBigDecimal("close"));
        //        tick.setLow(tickArr.getJSONObject(i).getBigDecimal("low"));
        //        tick.setHigh(tickArr.getJSONObject(i).getBigDecimal("high"));
        //        tick.setVolume(tickArr.getJSONObject(i).getBigDecimal("vol"));
        //        // 新加坡时间的时间戳，单位秒，并以此作为此huobi K线柱的id
        //        tick.setTimeStamp(DateUtils.getEpochMilliByTime(tickArr.getJSONObject(i).getLong("id") * 1000));
        //
        //        Map<String, String> reserveMap = new HashMap<>();
        //        reserveMap.put("count", tickArr.getJSONObject(i).getString("count"));
        //        reserveMap.put("amount", tickArr.getJSONObject(i).getString("amount"));
        //        tick.setReserveMap(reserveMap);
        //
        //        ticks.add(tick);
        //    }
        //    // 需要按时间周期进行过滤返回数据
        //    if (start != null && end != null) {
        //        ticks = ticks.stream()
        //                .filter(t -> t.getTimeStamp().compareTo(start) > 0 && t.getTimeStamp().compareTo(end) < 0)
        //                .collect(Collectors.toList());
        //    }
        //    return ticks;
        //} else {
        //    throw new GatewayException("获取huobi kline数据请求失败,statusCode:" + exchangeResp.getStatusCode());
        //}

        return handler.getCandleTicks();
    }

    /**
     * huobi 处理历史kline数据handler
     */
    private class HuobiHistoryKlineHandler extends CompressionWebSocketHandler {
        private String subscription;
        private long start;
        private long end;
        private List<CandleTick> candleTicks = new ArrayList<>();

        public HuobiHistoryKlineHandler(BinaryMessageCompression compression, String subscription, long start, long end) {
            super(compression);
            this.subscription = subscription;
            this.start = start;
            this.end = end;
        }

        @Override
        public void afterConnectionEstablished(WebSocketSession session) throws Exception {
            super.afterConnectionEstablished(session);
            JSONObject msg = new JSONObject();
            msg.put("req", subscription);
            msg.put("id", System.currentTimeMillis());
            msg.put("from", start);
            msg.put("to", end);
            log.info("HistoryKlineHandler订阅消息:{}", msg.toJSONString());
            session.sendMessage(new TextMessage(msg.toJSONString()));
            log.info("huobi HistoryKlineHandler 连接建立...");
        }

        @SneakyThrows
        @Override
        protected void handleDecompressBinaryMessage(WebSocketSession session, TextMessage decompressedMessage) {
            //log.info("{}", decompressedMessage.getPayload());

            JSONObject jsonMsg = JSONObject.parseObject(decompressedMessage.getPayload());
            // 发送pong心跳消息
            if (jsonMsg.containsKey("ping")) {
                try {
                    JSONObject pong = new JSONObject();
                    pong.put("pong", jsonMsg.getString("ping"));

                    session.sendMessage(new TextMessage(pong.toJSONString()));
                } catch (IOException e) {
                    log.error("huobi HistoryKlineHandler 发送pong异常,pingMsg:{},e:{}", jsonMsg.getString("ping"), e.toString());
                }
            }
            // 处理推送的kline数据
            if (jsonMsg.containsKey("data")) {
                JSONArray data = jsonMsg.getJSONArray("data");
                for (int i = 0; i < data.size(); i++) {
                    JSONObject klineTick = data.getJSONObject(i);

                    CandleTick candleTick = new CandleTick();
                    candleTick.setHigh(klineTick.getBigDecimal("high"));
                    candleTick.setLow(klineTick.getBigDecimal("low"));
                    candleTick.setOpen(klineTick.getBigDecimal("open"));
                    candleTick.setClose(klineTick.getBigDecimal("close"));
                    candleTick.setVolume(klineTick.getBigDecimal("amount"));
                    // id返回时间戳为s
                    candleTick.setTimeStamp(DateUtils.getEpochMilliByTime(klineTick.getLong("id") * 1000));

                    Map<String, String> reserveMap = new HashMap<>();
                    reserveMap.put("count", klineTick.getString("count"));
                    reserveMap.put("vol", klineTick.getString("vol"));
                    candleTick.setReserveMap(reserveMap);

                    candleTicks.add(candleTick);
                }

                log.info("当前线程:{},candleTicks size:{}", Thread.currentThread().getName(), candleTicks.size());

                //if (this.candleTicks.size() >= 600) {
                //    log.info("SAVED MESSAGE >= 300!!, candleTicks={}", candleTicks);
                //    session.close(CloseStatus.NORMAL);
                //}
            }
        }

        @Override
        public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
            super.afterConnectionClosed(session, status);
        }

        List<CandleTick> getCandleTicks() {
            return candleTicks;
        }
    }


    private List<String> addSubscription(Market market, List<CandleInterval> intervals, boolean trades, boolean orderBook, boolean tricks24h) {
        List<String> subscribeList = new ArrayList<>();
        List<CandleInterval> intervalList = intervals == null ? supportCandleIntervals() : intervals;
        final String symbol = exchangeApi.getCoinPairSymbolConverter().coinPairToSymbol(market.getCoinPair());
        for (CandleInterval interval : intervalList) {
            subscribeList.add(String.format("market.%s.kline.%s", symbol, interval.getSymbol()));
        }
        if (trades) {
            subscribeList.add(String.format("market.%s.trade.detail", symbol));
        }
        if (orderBook) {
            subscribeList.add(String.format("market.%s.depth.step0", symbol));
            subscribeList.add(String.format("market.%s.depth.step1", symbol));
            subscribeList.add(String.format("market.%s.depth.step2", symbol));
            subscribeList.add(String.format("market.%s.depth.step3", symbol));
            subscribeList.add(String.format("market.%s.depth.step4", symbol));
            subscribeList.add(String.format("market.%s.depth.step5", symbol));
        }
        if (tricks24h) {
            subscribeList.add(String.format("market.%s.detail", symbol));
        }
        return subscribeList;
    }
}
