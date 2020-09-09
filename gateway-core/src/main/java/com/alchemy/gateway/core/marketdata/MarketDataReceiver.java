package com.alchemy.gateway.core.marketdata;

import com.alchemy.gateway.core.common.CandleInterval;

import java.util.List;


/**
 * 市场数据接收器
 */
public interface MarketDataReceiver {

    /**
     * 接收到K线数据
     *
     * @param candleInterval K线周期
     * @param candleTick     K线数据(一条)
     */
    void receiveCandles(CandleInterval candleInterval, CandleTick candleTick);


    /**
     * 接收到深度数据
     *
     * @param orderBook 深度数据
     */
    void receiveOrderBook(OrderBook orderBook);

    /**
     * 接收到成交数据
     *
     * @param tradeTicks 成交数据
     */
    void receiveTrades(List<TradeTick> tradeTicks);

    /**
     * 接收到24小时Ticker数据
     *
     * @param ticker 24小时Ticker数据
     */
    void receive24Tickers(Ticker ticker);
}
