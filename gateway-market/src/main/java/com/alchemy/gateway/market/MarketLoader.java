package com.alchemy.gateway.market;

import java.util.List;

public interface MarketLoader {
    /**
     * 装载交易所市场
     *
     * @return 交易所市场列表
     */
    List<ExchangeMarket> loadExchangeMarkets();
}
