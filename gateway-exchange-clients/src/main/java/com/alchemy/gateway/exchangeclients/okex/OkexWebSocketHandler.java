package com.alchemy.gateway.exchangeclients.okex;

import com.alchemy.gateway.core.common.CandleInterval;
import com.alchemy.gateway.core.marketdata.*;
import com.alchemy.gateway.core.utils.DateUtils;
import com.alchemy.gateway.core.websocket.BinaryMessageCompression;
import com.alchemy.gateway.core.websocket.CompressionWebSocketHandler;
import com.alchemy.gateway.exchangeclients.okex.resultModel.SpotKline;
import com.alchemy.gateway.exchangeclients.okex.resultModel.SpotTicker;
import com.alchemy.gateway.exchangeclients.okex.resultModel.SpotTrade;
import com.alchemy.gateway.exchangeclients.okex.resultModel.orderBook.OrderBookItem;
import com.alchemy.gateway.exchangeclients.okex.resultModel.orderBook.SpotOrderBook;
import com.alchemy.gateway.exchangeclients.okex.resultModel.orderBook.SpotOrderBookDiff;
import com.alchemy.gateway.exchangeclients.okex.resultModel.orderBook.SpotOrderBookItem;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
public class OkexWebSocketHandler extends CompressionWebSocketHandler {

    private final List<String> subscribeList;
    private final MarketDataReceiver dataReceiver;
    private final List<CandleInterval> intervalList;

    private Map<String, Optional<SpotOrderBook>> bookMap = new HashMap<>();
    private ScheduledExecutorService service;
    private final static HashFunction crc32 = Hashing.crc32();


    public OkexWebSocketHandler(BinaryMessageCompression compression, List<String> subscribeList, MarketDataReceiver dataReceiver, List<CandleInterval> intervalList) {
        super(compression);
        this.subscribeList = subscribeList;
        this.dataReceiver = dataReceiver;
        this.intervalList = intervalList;
    }

    @Override
    protected void handleDecompressBinaryMessage(WebSocketSession session, TextMessage decompressedMessage) {
        String result = decompressedMessage.getPayload();
        if (result.contains("\"table\":\"spot/depth\",")) {//深度接口
            dataReceiver.receiveOrderBook(handleOrderBookList(session, result));
        }
        if (result.contains("\"table\":\"spot/depth5\",")) {//深度5档接口
            String dataStr = getResultJson(result);
            Optional<SpotOrderBook> spotOrderBook = parse(dataStr);
            dataReceiver.receiveOrderBook(handleOrderBook(spotOrderBook, 1));//1代表5档深度
        }
        if (result.contains("\"table\":\"spot/ticker\",")) {//24小时ticker
            String dataStr = getResultJson(result);
            dataReceiver.receive24Tickers(handleTickerData(dataStr));
        }
        if (result.contains("\"table\":\"spot/trade\",")) {//最近成交
            String dataStr = getResultJson(result);
            dataReceiver.receiveTrades(handleTrade(dataStr));
        }
        if (result.contains("\"table\":\"spot/candle")) {//Kline
            intervalList.forEach(candleInterval -> {
                if (result.contains(candleInterval.getMilliSeconds() / 1000 + "s\",")) {
                    String dataStr = getResultJson(result);
                    dataReceiver.receiveCandles(candleInterval, handleCandle(dataStr));
                }
            });
        }

    }

    /**
     * K线
     *
     * @param dataStr 解析接收到的json的data
     * @return CandleTick
     */
    private CandleTick handleCandle(String dataStr) {
        SpotKline spotKline = JSONObject.parseObject(dataStr, SpotKline.class);
        CandleTick candleTick = new CandleTick();
        candleTick.setTimeStamp(DateUtils.getTimeByUtc(spotKline.getCandle().get(0)));
        candleTick.setOpen(new BigDecimal(spotKline.getCandle().get(1)));
        candleTick.setHigh(new BigDecimal(spotKline.getCandle().get(2)));
        candleTick.setLow(new BigDecimal(spotKline.getCandle().get(3)));
        candleTick.setClose(new BigDecimal(spotKline.getCandle().get(4)));
        candleTick.setVolume(new BigDecimal(spotKline.getCandle().get(5)));
        return candleTick;
    }

    /**
     * 最近成交
     *
     * @param dataStr 解析接收到的json的data
     * @return TradeTick
     */
    private List<TradeTick> handleTrade(String dataStr) {
        SpotTrade spotTrade = JSONObject.parseObject(dataStr, SpotTrade.class);
        TradeTick trade = new TradeTick();
        trade.setPrice(new BigDecimal(spotTrade.getPrice()));
        trade.setSide(spotTrade.getSide());
        trade.setTimeStamp(DateUtils.getTimeByUtc(spotTrade.getTimestamp()));
        trade.setVolume(new BigDecimal(spotTrade.getSize()));
        trade.setTradeId(spotTrade.getTrade_id());

        List<TradeTick> items = new ArrayList<>();
        items.add(trade);
        return items;
    }

    /**
     * 24小时ticker
     *
     * @param dataStr 解析接收到的json的data
     * @return Ticker
     */
    private Ticker handleTickerData(String dataStr) {
        SpotTicker spotTicker = JSONObject.parseObject(dataStr, SpotTicker.class);
        Ticker ticker = new Ticker();
        ticker.setHigh(new BigDecimal(spotTicker.getHigh_24h()));
        ticker.setLow(new BigDecimal(spotTicker.getLow_24h()));
        ticker.setOpen(new BigDecimal(spotTicker.getOpen_24h()));
        ticker.setTimeStamp(DateUtils.getTimeByUtc(spotTicker.getTimestamp()));
        ticker.setAsk1Price(new BigDecimal(spotTicker.getBest_ask()));
        ticker.setAsk1Volume(new BigDecimal(spotTicker.getBest_ask_size()));
        ticker.setBid1Price(new BigDecimal(spotTicker.getBest_bid()));
        ticker.setBid1Volume(new BigDecimal(spotTicker.getBest_bid_size()));
        ticker.setAmount(new BigDecimal(spotTicker.getBase_volume_24h()));
        ticker.setVolume(new BigDecimal(spotTicker.getQuote_volume_24h()));
        ticker.setVolumeClose(new BigDecimal(spotTicker.getLast_qty()));
        ticker.setClose(new BigDecimal(spotTicker.getLast()));
        ticker.setSymbol(spotTicker.getInstrument_id());
        return ticker;
    }

    /**
     * 解析接收到的json的data
     *
     * @param result json
     * @return data
     */
    private String getResultJson(String result) {
        JSONObject rst = JSONObject.parseObject(result);
        JSONArray dataArr = JSONArray.parseArray(String.valueOf(rst.get("data")));
        JSONObject data = JSONObject.parseObject(String.valueOf(dataArr.get(0)));
        return String.valueOf(data);
    }

    /**
     * 400深度数据合并
     *
     * @param session socket会话
     * @param result  接收的数据
     */
    private OrderBook handleOrderBookList(WebSocketSession session, String result) {
        if (result.contains("partial")) {//是第一次的200档,记录下第一次的200档
            JSONObject rst = JSONObject.parseObject(result);
            JSONArray dataArr = JSONArray.parseArray(String.valueOf(rst.get("data")));
            JSONObject data = JSONObject.parseObject(String.valueOf(dataArr.get(0)));
            String dataStr = String.valueOf(data);
            Optional<SpotOrderBook> orderBook = parse(dataStr);
            String instrumentId = String.valueOf(data.get("instrument_id"));
            bookMap.put(instrumentId, orderBook);
        } else if (result.contains("\"action\":\"update\",")) {//是后续的增量，则需要进行深度合并
            JSONObject rst = JSONObject.parseObject(result);
            JSONArray dataArr = JSONArray.parseArray(String.valueOf(rst.get("data")));
            JSONObject data = JSONObject.parseObject(String.valueOf(dataArr.get(0)));
            String dataStr = String.valueOf(data);
            String instrumentId = String.valueOf(data.get("instrument_id"));
            Optional<SpotOrderBook> oldBook = bookMap.get(instrumentId);
            Optional<SpotOrderBook> newBook = parse(dataStr);
            SpotOrderBookDiff bookDiff = oldBook.get().diff(newBook.get());
            //log.info("合并后的深度数据:{}", bookDiff.toString());
            String str = getStr(bookDiff.getAsks(), bookDiff.getBids());
            int checksum = checksum(bookDiff.getAsks(), bookDiff.getBids());
            boolean flag = checksum == bookDiff.getChecksum();
            if (flag) {
                oldBook = parse(bookDiff.toString());
                bookMap.put(instrumentId, oldBook);
            } else {
                //获取订阅的频道和币对
                try {
                    log.info("名称：{},深度校验结果为：{},数据有误！请重连！", instrumentId, false);
                    String channel = rst.get("table").toString();
                    String unSubStr = "{\"op\": \"unsubscribe\", \"args\":[\"" + channel + ":" + instrumentId + "\"]}";
                    session.sendMessage(new TextMessage(unSubStr));
                    String subStr = "{\"op\": \"subscribe\", \"args\":[\"" + channel + ":" + instrumentId + "\"]}";
                    session.sendMessage(new TextMessage(subStr));
                } catch (IOException e) {
                    log.error("重新订阅时发生异常", e);
                }
            }
        }
        OrderBook orderBook = new OrderBook();
        for (Map.Entry<String, Optional<SpotOrderBook>> item : bookMap.entrySet()) {
            Optional<SpotOrderBook> itemValue = item.getValue();
            orderBook = handleOrderBook(itemValue, 0);//0代表400全量档深度
        }
        return orderBook;
    }

    /**
     * 深度数据处理
     *
     * @param spotOrderBook 深度数据
     * @param level         档级
     * @return 处理好的深度数据
     */
    private OrderBook handleOrderBook(Optional<SpotOrderBook> spotOrderBook, int level) {
        OrderBook orderBook = new OrderBook();
        if (spotOrderBook.isPresent()) {
            List<OrderBookEntry> asks = new ArrayList<>();
            spotOrderBook.get().getAsks().forEach(ack -> {
                OrderBookEntry orderBookEntry = new OrderBookEntry();
                orderBookEntry.setPrice(new BigDecimal(ack.getPrice()));
                orderBookEntry.setSize(new BigDecimal(ack.getSize()));
                Map<String, String> ackMap = new HashMap<>();
                ackMap.put("numOrder", ack.getNumOrder());
                orderBookEntry.setReserveMap(ackMap);
                asks.add(orderBookEntry);
            });
            List<OrderBookEntry> bids = new ArrayList<>();
            spotOrderBook.get().getBids().forEach(bid -> {
                OrderBookEntry orderBookEntry = new OrderBookEntry();
                orderBookEntry.setPrice(new BigDecimal(bid.getPrice()));
                orderBookEntry.setSize(new BigDecimal(bid.getSize()));
                Map<String, String> bidMap = new HashMap<>();
                bidMap.put("numOrder", bid.getNumOrder());
                orderBookEntry.setReserveMap(bidMap);
                bids.add(orderBookEntry);
            });
            orderBook.setAsks(asks);
            orderBook.setBids(bids);
            orderBook.setTimeStamp(DateUtils.getTimeByUtc(spotOrderBook.get().getTimestamp()));
            orderBook.setLevel(level);
        }
        return orderBook;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);

        log.info("Okex 行情数据推送连接建立");

        String subscribeString = listToJson(subscribeList);
        String str = "{\"op\": \"subscribe\", \"args\":" + subscribeString + "}";

        session.sendMessage(new TextMessage(str));
        log.info("Okex 订阅信息:" + str);

        service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(() -> {
            try {
                session.sendMessage(new TextMessage("ping"));
            } catch (IOException e) {
                log.error("发送 ping 消息时发生异常", e);
            }
        }, 25, 25, TimeUnit.SECONDS);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        log.info("okex断开webSocket连接,CloseStatus:{}", status);
        service.shutdown();
    }

    /**
     * list转换成json
     *
     * @param list 订阅频道集合
     * @return 转换后的json
     */
    private String listToJson(final List<String> list) {
        final JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(list);
        return jsonArray.toJSONString();
    }

    public static <T extends OrderBookItem> int checksum(List<T> asks, List<T> bids) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < 25; i++) {
            if (i < bids.size()) {
                s.append(bids.get(i).getPrice());
                s.append(":");
                s.append(bids.get(i).getSize());
                s.append(":");
            }
            if (i < asks.size()) {
                s.append(asks.get(i).getPrice());
                s.append(":");
                s.append(asks.get(i).getSize());
                s.append(":");
            }
        }
        final String str;
        if (s.length() > 0) {
            str = s.substring(0, s.length() - 1);
        } else {
            str = "";
        }

        return crc32.hashString(str, StandardCharsets.UTF_8).asInt();
    }

    private static <T extends OrderBookItem> String getStr(List<T> asks, List<T> bids) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < 25; i++) {
            if (i < bids.size()) {
                s.append(bids.get(i).getPrice());
                s.append(":");
                s.append(bids.get(i).getSize());
                s.append(":");
            }
            if (i < asks.size()) {
                s.append(asks.get(i).getPrice());
                s.append(":");
                s.append(asks.get(i).getSize());
                s.append(":");
            }
        }
        final String str;
        if (s.length() > 0) {
            str = s.substring(0, s.length() - 1);
        } else {
            str = "";
        }
        return str;
    }

    public static Optional<SpotOrderBook> parse(String json) {
        try {
            OrderBookData data = JSONObject.parseObject(json, OrderBookData.class);
            List<SpotOrderBookItem> asks =
                    data.getAsks().stream().map(x -> new SpotOrderBookItem(x.get(0), x.get(1), x.get(2)))
                            .collect(Collectors.toList());

            List<SpotOrderBookItem> bids =
                    data.getBids().stream().map(x -> new SpotOrderBookItem(x.get(0), x.get(1), x.get(2)))
                            .collect(Collectors.toList());

            return Optional.of(new SpotOrderBook(data.getInstrument_id(), asks, bids, data.getTimestamp(), data.getChecksum()));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Data
    static class OrderBookData {
        private String instrument_id;
        private List<List<String>> asks;
        private List<List<String>> bids;
        private String timestamp;
        private int checksum;
    }
}
