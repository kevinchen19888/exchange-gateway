package com.alchemy.gateway.quotation.service;

import com.alchemy.gateway.market.ExchangeMarket;

import java.util.List;

/**
 * 行情历史数据提取器
 */
public interface QuotationHistoryFetcher {
    /**
     * K线历史
     *
     * @param exchangeMarketList 交易所币对信息
     */
    void klineHistory(List<ExchangeMarket> exchangeMarketList);

}
