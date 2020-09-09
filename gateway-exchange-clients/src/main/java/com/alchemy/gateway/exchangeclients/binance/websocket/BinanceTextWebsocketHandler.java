package com.alchemy.gateway.exchangeclients.binance.websocket;

import com.alchemy.gateway.core.common.CandleInterval;
import com.alchemy.gateway.core.marketdata.*;
import com.alchemy.gateway.core.utils.DateUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kevin chen
 */
@Slf4j
public class BinanceTextWebsocketHandler extends AbstractWebSocketHandler {
    private final MarketDataReceiver dataReceiver;

    public BinanceTextWebsocketHandler(MarketDataReceiver dataReceiver) {
        this.dataReceiver = dataReceiver;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        // binance ping&pong 保持连接通过协议层实现不需要额外处理
        log.info("binance websocket获取行情数据连接建立...");
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        JSONObject topicMsg = JSON.parseObject(message.getPayload());
        JSONObject data = topicMsg.getJSONObject("data");
        String stream = topicMsg.getString("stream");

        // 根据stream名称处理不同的消息
        // kline数据
        if (stream.contains("@kline_")) {
            JSONObject kline = data.getJSONObject("k");
            CandleTick candle = new CandleTick();
            candle.setClose(kline.getBigDecimal("c"));
            candle.setHigh(kline.getBigDecimal("h"));
            candle.setLow(kline.getBigDecimal("l"));
            candle.setOpen(kline.getBigDecimal("o"));
            candle.setTimeStamp(DateUtils.getEpochMilliByTime(kline.getLong("t")));
            candle.setVolume(kline.getBigDecimal("v"));

            Map<String, String> reserveMap = new HashMap<>();
            reserveMap.put("close_time", DateUtils.getTimeByEpochMilli(kline.getLong("T")));
            candle.setReserveMap(reserveMap);

            String internal = kline.getString("i");
            dataReceiver.receiveCandles(CandleInterval.fromSymbol(internal), candle);
        }
        // ticker数据
        if (stream.contains("@miniTicker")) {
            Ticker ticker = new Ticker();
            ticker.setHigh(data.getBigDecimal("h"));
            ticker.setLow(data.getBigDecimal("l"));
            ticker.setOpen(data.getBigDecimal("o"));
            ticker.setClose(data.getBigDecimal("c"));
            ticker.setVolume(data.getBigDecimal("v"));
            ticker.setAmount(data.getBigDecimal("q"));
            ticker.setTimeStamp(DateUtils.getEpochMilliByTime(data.getLong("E")));
            ticker.setSymbol(data.getString("s"));

            dataReceiver.receive24Tickers(ticker);
        }
        // 深度数据
        if (stream.contains("@depth10")) {
            OrderBook orderBook = new OrderBook();
            orderBook.setLevel(0);
            JSONArray bids = data.getJSONArray("bids");
            List<OrderBookEntry> bidsList = new ArrayList<>();
            for (int i = 0; i < bids.size(); i++) {
                OrderBookEntry book = new OrderBookEntry();
                JSONArray bidArr = bids.getJSONArray(i);
                book.setPrice(bidArr.getBigDecimal(0));
                book.setSize(bidArr.getBigDecimal(1));
                bidsList.add(book);
            }
            orderBook.setBids(bidsList);

            JSONArray asks = data.getJSONArray("asks");
            List<OrderBookEntry> asksList = new ArrayList<>();
            for (int i = 0; i < asks.size(); i++) {
                JSONArray askArr = asks.getJSONArray(i);
                OrderBookEntry book = new OrderBookEntry();
                book.setPrice(askArr.getBigDecimal(0));
                book.setSize(askArr.getBigDecimal(1));
                asksList.add(book);
            }
            orderBook.setAsks(asksList);
            orderBook.setTimeStamp(LocalDateTime.now(ZoneOffset.UTC));
            dataReceiver.receiveOrderBook(orderBook);
        }
        // 最近成交
        if (stream.contains("@trade")) {
            List<TradeTick> tickList = new ArrayList<>();
            TradeTick tick = new TradeTick();
            tick.setTradeId(data.getString("t"));
            tick.setPrice(data.getBigDecimal("p"));
            tick.setVolume(data.getBigDecimal("q"));
            tick.setTimeStamp(DateUtils.getEpochMilliByTime(data.getLong("T")));
            tick.setSide(Boolean.TRUE.equals(data.getBoolean("m")) ? "sell" : "buy");

            Map<String, String> reserveMap = new HashMap<>();
            reserveMap.put("symbol", data.getString("s"));
            tick.setReserveMap(reserveMap);

            tickList.add(tick);
            dataReceiver.receiveTrades(tickList);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
    }

}
