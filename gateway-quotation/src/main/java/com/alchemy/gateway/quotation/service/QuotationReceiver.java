package com.alchemy.gateway.quotation.service;

import com.alchemy.gateway.market.ExchangeMarket;

/**
 * 行情数据接收器（WebScoket）
 */
public interface QuotationReceiver {
    /**
     * 订阅
     *
     * @param exchangeMarket 币对信息
     */
    void subscribe(ExchangeMarket exchangeMarket);
}
