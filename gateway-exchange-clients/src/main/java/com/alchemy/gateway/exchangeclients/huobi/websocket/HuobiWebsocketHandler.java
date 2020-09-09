package com.alchemy.gateway.exchangeclients.huobi.websocket;

import com.alchemy.gateway.core.common.CandleInterval;
import com.alchemy.gateway.core.marketdata.*;
import com.alchemy.gateway.core.utils.DateUtils;
import com.alchemy.gateway.core.websocket.BinaryMessageCompression;
import com.alchemy.gateway.core.websocket.CompressionWebSocketHandler;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kevin chen
 */
@Slf4j
public class HuobiWebsocketHandler extends CompressionWebSocketHandler {
    private final List<String> subscribeList;
    private final MarketDataReceiver dataReceiver;

    public HuobiWebsocketHandler(BinaryMessageCompression compression, List<String> subscribeList, MarketDataReceiver dataReceiver) {
        super(compression);
        this.subscribeList = subscribeList;
        this.dataReceiver = dataReceiver;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 连接建立后发送订阅消息
        super.afterConnectionEstablished(session);
        for (String topic : subscribeList) {
            JSONObject msg = new JSONObject();
            msg.put("sub", topic);
            msg.put("id", System.currentTimeMillis());

            session.sendMessage(new TextMessage(msg.toJSONString()));
        }
        log.info("huobi websocket连接建立...");
    }

    @Override
    protected void handleDecompressBinaryMessage(WebSocketSession session, TextMessage decompressedMessage) {
        JSONObject jsonMsg = JSONObject.parseObject(decompressedMessage.getPayload());
        // 发送pong心跳消息
        if (jsonMsg.containsKey("ping")) {
            try {
                JSONObject pong = new JSONObject();
                pong.put("pong", jsonMsg.getString("ping"));

                session.sendMessage(new TextMessage(pong.toJSONString()));
            } catch (IOException e) {
                log.error("huobi发送pong消息异常,ping msg:{},e:{}", jsonMsg.getString("ping"), e.toString());
            }
        }

        if (jsonMsg.containsKey("ch")) {
            // 处理推送的kline数据
            final String tag = jsonMsg.getString("ch");

            handleKlineData(jsonMsg, tag);

            handleTradeData(jsonMsg, tag);

            handle24hTickerData(jsonMsg, tag);

            handleDepthData(jsonMsg, tag);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
    }

    private void handleDepthData(JSONObject jsonMsg, String tag) {
        // 深度数据
        if (tag.contains("depth")) {
            JSONObject tickMsg = jsonMsg.getJSONObject("tick");
            OrderBook orderBook = new OrderBook();
            JSONArray bids = tickMsg.getJSONArray("bids");
            List<OrderBookEntry> bidsList = new ArrayList<>();
            for (int i = 0; i < bids.size(); i++) {
                JSONArray bid = bids.getJSONArray(i);
                OrderBookEntry bidEntry = new OrderBookEntry();
                bidEntry.setPrice(bid.getBigDecimal(0));
                bidEntry.setSize(bid.getBigDecimal(1));
                bidsList.add(bidEntry);
            }
            orderBook.setBids(bidsList);

            List<OrderBookEntry> askList = new ArrayList<>();
            JSONArray asks = tickMsg.getJSONArray("asks");
            for (int i = 0; i < asks.size(); i++) {
                JSONArray ask = asks.getJSONArray(i);
                OrderBookEntry askEntry = new OrderBookEntry();
                askEntry.setPrice(ask.getBigDecimal(0));
                askEntry.setSize(ask.getBigDecimal(1));
                askList.add(askEntry);
            }
            orderBook.setAsks(askList);
            orderBook.setTimeStamp(DateUtils.getEpochMilliByTime(jsonMsg.getLong("ts")));

            if (tag.contains("step0")) {
                orderBook.setLevel(0);
            }
            if (tag.contains("step1")) {
                orderBook.setLevel(1);
            }
            if (tag.contains("step2")) {
                orderBook.setLevel(2);
            }
            if (tag.contains("step3")) {
                orderBook.setLevel(3);
            }
            if (tag.contains("step4")) {
                orderBook.setLevel(4);
            }
            if (tag.contains("step5")) {
                orderBook.setLevel(5);
            }
            dataReceiver.receiveOrderBook(orderBook);
        }
    }

    private void handle24hTickerData(JSONObject jsonMsg, String tag) {
        // 24h ticker数据
        if (tag.contains(".detail") && !tag.contains("trade")) {
            JSONObject tickMsg = jsonMsg.getJSONObject("tick");
            Ticker ticker = new Ticker();
            ticker.setOpen(tickMsg.getBigDecimal("open"));
            ticker.setClose(tickMsg.getBigDecimal("close"));
            ticker.setHigh(tickMsg.getBigDecimal("high"));
            ticker.setLow(tickMsg.getBigDecimal("low"));
            ticker.setVolume(tickMsg.getBigDecimal("amount"));
            ticker.setAmount(tickMsg.getBigDecimal("vol"));
            ticker.setTimeStamp(DateUtils.getEpochMilliByTime(jsonMsg.getLong("ts")));

            Map<String, String> reserveMap = new HashMap<>();
            reserveMap.put("count", tickMsg.getString("count"));
            ticker.setReserveMap(reserveMap);
            dataReceiver.receive24Tickers(ticker);
        }
    }

    private void handleTradeData(JSONObject jsonMsg, String tag) {
        // 最新成交数据
        if (tag.contains("trade.detail")) {
            JSONArray data = jsonMsg.getJSONObject("tick").getJSONArray("data");
            List<TradeTick> ticks = new ArrayList<>();
            for (int i = 0; i < data.size(); i++) {
                JSONObject trade = data.getJSONObject(i);
                TradeTick tradeTick = new TradeTick();
                tradeTick.setVolume(trade.getBigDecimal("amount"));
                tradeTick.setPrice(trade.getBigDecimal("price"));
                tradeTick.setSide(trade.getString("direction"));
                tradeTick.setTradeId(trade.getString("tradeId"));
                tradeTick.setTimeStamp(DateUtils.getEpochMilliByTime(trade.getLong("ts")));

                ticks.add(tradeTick);
            }
            dataReceiver.receiveTrades(ticks);
        }
    }

    private void handleKlineData(JSONObject jsonMsg, String tag) {
        if (tag.contains("kline")) {
            CandleTick tick = new CandleTick();
            JSONObject klineTick = jsonMsg.getJSONObject("tick");
            tick.setHigh(klineTick.getBigDecimal("high"));
            tick.setLow(klineTick.getBigDecimal("low"));
            tick.setOpen(klineTick.getBigDecimal("open"));
            tick.setClose(klineTick.getBigDecimal("close"));
            tick.setVolume(klineTick.getBigDecimal("amount"));
            // id返回时间戳为s
            tick.setTimeStamp(DateUtils.getEpochMilliByTime(klineTick.getLong("id") * 1000));

            Map<String, String> reserveMap = new HashMap<>();
            reserveMap.put("count", klineTick.getString("count"));
            reserveMap.put("vol", klineTick.getString("vol"));
            tick.setReserveMap(reserveMap);
            String[] tagArr = tag.split("kline.");// tag示例:market.btcusdt.kline.1min
            dataReceiver.receiveCandles(CandleInterval.fromSymbol(tagArr[1]), tick);
        }
    }
}
