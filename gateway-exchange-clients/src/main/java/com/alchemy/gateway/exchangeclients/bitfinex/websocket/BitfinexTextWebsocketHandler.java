package com.alchemy.gateway.exchangeclients.bitfinex.websocket;

import com.alchemy.gateway.core.marketdata.*;
import com.alchemy.gateway.core.utils.DateUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;


@Slf4j
public class BitfinexTextWebsocketHandler extends AbstractWebSocketHandler {
    private final MarketDataReceiver dataReceiver;
    private ScheduledExecutorService scheduledExecutor;

    private List<String> subscribeList;

    private AtomicLong atomicLong = new AtomicLong(1);


    private HashMap<Long, WsJsonObj> channelIdMap = new HashMap<>();

    private static  HashMap< String,HashMap<BigDecimal, OrderBookEntry>> preChannelMap=new HashMap<>();

    public BitfinexTextWebsocketHandler(MarketDataReceiver dataReceiver, List<String> msg) {
        this.dataReceiver = dataReceiver;
        this.subscribeList = msg;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);

        scheduledExecutor = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutor.scheduleAtFixedRate(() -> sendPing(session), 5, 30, TimeUnit.SECONDS);


        log.info("Bitfinex 行情数据推送连接建立");
        try {
            for (String sub : subscribeList) {
                log.info("Bitfinex 订阅 数据:{}", sub);
                session.sendMessage(new TextMessage(sub));
            }

        } catch (IOException e) {
            log.error("Bitfinex 订阅 数据时发生异常", e);
        }


    }

    public void sendPing(WebSocketSession session) {
        String ping = "{ \"event\":\"ping\",\"cid\":" + atomicLong.getAndIncrement() + " }";
        try {
            session.sendMessage(new TextMessage(ping));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * log.info("bitfinex  handleTextMessage data: {}", msg);
     * {"event":"subscribed","channel":"candles","chanId":515,"key":"trade:14d:tBTCUSD"}
     * {"event":"subscribed","channel":"trades","chanId":89,"symbol":"tBTCUSD","pair":"BTCUSD"}
     * {"event":"subscribed","channel":"ticker","chanId":517,"symbol":"tBTCUSD","pair":"BTCUSD"}
     * {"event":"subscribed","channel":"book","chanId":319,"symbol":"tBTCUSD","prec":"P0","freq":"F0","len":"25","pair":"BTCUSD"}
     *
     * @param session WebSocketSession
     * @param message message
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        String msg = message.getPayload();


        if (msg.indexOf("hb") > 0) {
            //log.info("msg  :{}", msg);

        } else if (msg.indexOf("pong") > 0) {
            //log.info("msg  :{}", msg);

        } else if (msg.indexOf("event") > 0) {
            WsJsonObj subObj = JSON.parseObject(msg, WsJsonObj.class);
            if (subObj.getChanId() != null) {
                log.info("订阅成功 ！ 返回:{}", subObj);
                channelIdMap.put(subObj.getChanId(), subObj);
                preChannelMap.put(subObj.getPrec()+subObj.getChanId(),new HashMap<>());
            }

        } else {
            JSONArray jsonArray = JSON.parseArray(msg);
            Long chId = jsonArray.getLong(0);
            WsJsonObj subObj = channelIdMap.get(chId);
            if (subObj == null) {
                log.error("bad ws msg:{}", msg);
                return;
            }

            JSONArray dataArray;
            if (msg.indexOf("te") > 0 || msg.indexOf("tu") > 0) {
                dataArray = jsonArray.getJSONArray(2);

            } else {
                dataArray = jsonArray.getJSONArray(1);
            }

            if (dataArray == null) {
                return;
            }


            switch (subObj.getChannelEnum()) {
                case BOOK:
                    dataReceiver.receiveOrderBook(getOrderBook(dataArray, subObj));
                    break;

                case TICKER:
                    dataReceiver.receive24Tickers(getTicker(dataArray, subObj));
                    break;

                case TRADES:
                    dataReceiver.receiveTrades(getTradeTickList(dataArray));
                    break;

                case CANDLES:
                    dataReceiver.receiveCandles(subObj.getSymbolInterval().getInterval(), getCandle(dataArray, subObj));
                    break;

                default:
                    throw new IllegalArgumentException("不支持的channel" + subObj.getChannelEnum());
            }

        }
    }

    /**
     * 注意 这里 没有买1和 卖1的数量，只有最近25档 累计的数量---坑货。。。
     * <p>
     * BID,
     * BID_SIZE,
     * ASK,
     * ASK_SIZE,
     * <p>
     * DAILY_CHANGE,
     * DAILY_CHANGE_RELATIVE,
     * <p>
     * LAST_PRICE,
     * VOLUME,
     * HIGH,
     * LOW
     * <p>
     * [953,[10978,65.22111603,10979,128.17129968,-95,-0.0086,10978,7285.11477842,11355,10917]]
     *
     * @param dataArray json array
     * @param subObj    subObj
     * @return Ticker
     */
    public static Ticker getTicker(JSONArray dataArray, WsJsonObj subObj) {

        Map<String, String> reserveMap = new HashMap<>();

        Ticker ticker = new Ticker();
        ticker.setSymbol(subObj.getSymbolInterval().getSymbol());

        ticker.setBid1Price(new BigDecimal(dataArray.getString(0)));
        ticker.setBid1Volume(new BigDecimal(dataArray.getString(1)));
        ticker.setAsk1Price(new BigDecimal(dataArray.getString(2)));
        ticker.setAsk1Volume(new BigDecimal(dataArray.getString(3)));

        ticker.setVolume(new BigDecimal(dataArray.getString(7)));
        ticker.setAmount(new BigDecimal(dataArray.getString(7)).multiply(ticker.getBid1Price()));


        ticker.setVolumeClose(null);
        ticker.setTimeStamp(LocalDateTime.now());
        //24h 涨跌 值，不是幅度
        BigDecimal var=new BigDecimal(dataArray.getString(4));
        ticker.setClose(new BigDecimal(dataArray.getString(6)));
        ticker.setOpen(ticker.getClose().subtract(var));
        ticker.setHigh(new BigDecimal(dataArray.getString(8)));
        ticker.setLow(new BigDecimal(dataArray.getString(9)));
        ticker.setReserveMap(Maps.newHashMap());


        reserveMap.put("symbol", subObj.getSymbolInterval().getSymbol());
        ticker.setReserveMap(reserveMap);

        return ticker;
    }


    /**
     * MTS, 0
     * OPEN, 1
     * CLOSE, 2
     * HIGH, 3
     * LOW, 4
     * VOLUME 5
     * <p>
     * [6071,[1596177720000,337.66,338.31,338.32,337.66,38.14299314]]
     *
     * @param dataArray json array
     * @param subObj    WsJsonObj
     * @return CandleTick
     */
    public static CandleTick getCandle(JSONArray dataArray, WsJsonObj subObj) {

        Map<String, String> reserveMap = new HashMap<>();

        boolean isArray = true;
        List<CandleTick> list = new ArrayList<>();
        for (int i = 0; i < dataArray.size(); i++) {
            Object obj = dataArray.get(i);
            if (obj instanceof JSONArray) {

                JSONArray items = dataArray.getJSONArray(i);
                CandleTick candle = new CandleTick();
                candle.setTimeStamp(DateUtils.getEpochMilliByTime(items.getLong(0)));
                candle.setHigh(items.getBigDecimal(3));
                candle.setOpen(items.getBigDecimal(1));
                candle.setLow(items.getBigDecimal(4));
                candle.setClose(items.getBigDecimal(2));
                candle.setVolume(items.getBigDecimal(5));
                reserveMap.put("symbol", subObj.getSymbolInterval().getSymbol());
                candle.setReserveMap(reserveMap);
                list.add(candle);
            } else {
                isArray = false;
                break;
            }

        }
        if (!isArray) {
            CandleTick candle = new CandleTick();
            candle.setTimeStamp(DateUtils.getEpochMilliByTime(dataArray.getLong(0)));
            candle.setHigh(dataArray.getBigDecimal(3));
            candle.setOpen(dataArray.getBigDecimal(1));
            candle.setLow(dataArray.getBigDecimal(4));
            candle.setClose(dataArray.getBigDecimal(2));
            candle.setVolume(dataArray.getBigDecimal(5));
            reserveMap.put("symbol", subObj.getSymbolInterval().getSymbol());
            candle.setReserveMap(reserveMap);
            list.add(candle);
        }


        return list.size() > 0 ? list.get(0) : null;

    }


    /**
     * : bitfinex ws on TRADES data:[[485894508,1596158303523,-0.00001618,11065.44584948],[485894506,1596158303523,-0.00061,11065.44584948],[485894504,1596158303523,-0.000615,11065.44584948],[485894502,1596158303523,-0.000615,11065.44584948],[485894500,1596158303523,-0.00061,11065.44584948],[485894498,1596158303523,-0.00061,11065.44584948],[485894496,1596158303523,-0.00061,11065.44584948],[485894494,1596158303523,-0.000611,11065.44584948],[485894492,1596158303523,-0.000611,11065.44584948],[485894490,1596158303523,-0.00061,11065.44584948],[485894488,1596158303523,-0.000611,11065.44584948],[485894486,1596158303523,-0.00061,11065.44584948],[485894484,1596158303523,-0.00061,11065.44584948],[485894482,1596158303523,-0.00061,11065.44584948],[485894480,1596158303523,-0.00066,11065.80337053],[485894478,1596158303523,-0.00066,11065.80337053],[485894476,1596158303523,-0.00066,11065.80337053],[485894474,1596158303523,-0.00066,11065.80337053],[485894472,1596158303523,-0.000661,11065.80337053],[485894470,1596158303523,-0.000661,11065.80337053],[485894468,1596158303523,-0.000661,11065.80337053],[485894466,1596158303523,-0.00066,11065.80337053],[485894464,1596158303523,-0.00066,11065.80337053],[485894462,1596158303523,-0.00066,11065.80337053],[485894460,1596158303523,-0.000665,11065.80337053],[485894458,1596158303523,-0.000665,11065.80337053],[485894456,1596158303523,-0.00066,11065.80337053],[485894454,1596158303523,-0.000665,11065.80337053],[485894452,1596158303523,-0.00066,11065.80337053],[485894450,1596158303523,-0.00066,11065.80337053]]
     *
     * @param dataArray JSONArray
     * @return TradeTick
     */
    public static List<TradeTick> getTradeTickList(JSONArray dataArray) {
        List<TradeTick> list = new ArrayList<>();

        boolean isArray = true;
        for (Object obj : dataArray) {
            if (obj instanceof JSONArray) {
                JSONArray items = (JSONArray) obj;
                TradeTick tick = new TradeTick();
                tick.setTradeId(items.getInteger(0).toString());
                tick.setPrice(items.getBigDecimal(3));
                tick.setTimeStamp(DateUtils.getEpochMilliByTime(items.getLong(1)));
                tick.setSide(items.getBigDecimal(2).compareTo(BigDecimal.ZERO) > 0 ? "buy" : "sell");
                tick.setVolume(items.getBigDecimal(2).abs());
                tick.setReserveMap(null);

                list.add(tick);

            } else {
                isArray = false;
                break;
            }

        }
        if (!isArray) {
            TradeTick tick = new TradeTick();
            tick.setTradeId(dataArray.getInteger(0).toString());
            tick.setPrice(dataArray.getBigDecimal(3));
            tick.setTimeStamp(DateUtils.getEpochMilliByTime(dataArray.getLong(1)));
            tick.setSide(dataArray.getBigDecimal(2).compareTo(BigDecimal.ZERO) > 0 ? "buy" : "sell");
            tick.setVolume(dataArray.getBigDecimal(2).abs());
            tick.setReserveMap(null);

            list.add(tick);
        }

        return list;
    }


    private static void putOrderMap(String pre, BigDecimal price, BigDecimal amount,Long channelId) {
        OrderBookEntry entry = new OrderBookEntry();
        entry.setPrice(price);
        entry.setSize(amount.abs());
        entry.getReserveMap().put("isBid", amount.compareTo(BigDecimal.ZERO) > 0 ? "1" : "0");
        getOrderBookMap(pre,channelId).put(price, entry);
    }

    private static void updateOrderMap(String pre, BigDecimal price, Integer count,BigDecimal amount,Long channelId) {

        if (count==0)
        {
            getOrderBookMap(pre,channelId).remove(price);
        }else
        {
            putOrderMap(pre,price,amount,channelId);
        }

    }


    private static HashMap<BigDecimal, OrderBookEntry> getOrderBookMap(String pre,Long channelId) {
        return  preChannelMap.get(pre+channelId);
    }

    /**
     * 根据精度 模式 以及内存的值 生成全量的 orderBook
     * @param pre  精度
     * @return 全量的 orderBook
     */
    private static OrderBook genFullOrderBook(String pre,Long channelId) {
        OrderBook orderBook = new OrderBook();
        orderBook.setTimeStamp(LocalDateTime.now());

        List<OrderBookEntry> asks = new ArrayList<>();
        List<OrderBookEntry> bids = new ArrayList<>();

        for (OrderBookEntry entry : getOrderBookMap(pre, channelId).values()) {
            if (entry.getReserveMap().get("isBid").equalsIgnoreCase("1")) {
                bids.add(entry);
            } else {
                asks.add(entry);
            }

        }
        orderBook.setAsks(asks);
        orderBook.setBids(bids);
        orderBook.setLevel(switchLevel(pre));
        orderBook.getAsks().sort(Comparator.comparing(OrderBookEntry::getPrice));
        orderBook.getBids().sort((a1, a2) -> a2.getPrice().compareTo(a1.getPrice()));
        return orderBook;
    }

    private static Integer switchLevel(String pre)
    {
        if ("P0".equalsIgnoreCase(pre)) {
           return 0;
        } else if ("P2".equalsIgnoreCase(pre)) {
            return 1;
        } else if ("P4".equalsIgnoreCase(pre)) {
            return 2;
        }
        return 0;
    }

    /**
     * [10983,8,10.97557612]
     *
     * @param dataArray json array
     * @return order book
     */
    public static OrderBook getOrderBook(JSONArray dataArray, WsJsonObj ws) {

        boolean isArray = true;
        for (Object obj : dataArray) {
            if (obj instanceof JSONArray) {
                JSONArray items = (JSONArray) obj;
                BigDecimal price=items.getBigDecimal(0);

                BigDecimal amount=items.getBigDecimal(2);
                putOrderMap(ws.getPrec(),price,amount,ws.getChanId());
            } else {
                isArray = false;
                break;
            }

        }
        if (!isArray) {
            //id  price  amount
            //price count amount
            BigDecimal price=dataArray.getBigDecimal(0);
            Integer count=dataArray.getInteger(1);
            BigDecimal amount=dataArray.getBigDecimal(2);

            updateOrderMap(ws.getPrec(),price,count,amount,ws.getChanId());

        }

        return genFullOrderBook(ws.getPrec(),ws.getChanId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        // 关闭连接后的清理工作
        scheduledExecutor.shutdown();
        log.error(" bitfinex ws 连接被关闭！");


    }

//    public void unsubscib(WebSocketSession session) {
//        UnSubscribeJson json = new UnSubscribeJson();
//
//        channelIdMap.keySet().forEach(ch -> {
//            json.setChanId(ch);
//            json.setEvent("unsubscribe");
//            try {
//                log.info(" 取消订阅 {}", json);
//                session.sendMessage(new TextMessage(json.toJson()));
//            } catch (Exception e) {
//                log.info("   取消订阅失败 {}", json);
//            }
//
//        });
//
//    }

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {
        log.info(" handleBinaryMessage {}", message);
    }

    @Override
    protected void handlePongMessage(WebSocketSession session, PongMessage message) {
        log.info(" handlePongMessage {}", message);
    }

}
