package com.alchemy.gateway.quotation.service;

import com.alchemy.gateway.core.common.CandleInterval;
import com.alchemy.gateway.core.marketdata.CandleTick;
import com.alchemy.gateway.core.marketdata.OrderBook;
import com.alchemy.gateway.core.marketdata.Ticker;
import com.alchemy.gateway.core.marketdata.TradeTick;
import com.alchemy.gateway.market.ExchangeMarket;

import java.util.List;

/**
 * 行情数据发送队列
 */
public interface QuotationSender {
    /**
     * K线
     */
    void sendKlines(CandleTick candleTick, ExchangeMarket coinPair, CandleInterval candleInterval);

    /**
     * 24小时ticker数据
     */
    void sendTickers(Ticker ticker, ExchangeMarket coinPair);

    /**
     * 深度
     */
    void sendOrderBooks(OrderBook orderBook, ExchangeMarket coinPair);

    /**
     * 最近成交
     */
    void sendTrades(List<TradeTick> tradeTick, ExchangeMarket coinPair);
}
