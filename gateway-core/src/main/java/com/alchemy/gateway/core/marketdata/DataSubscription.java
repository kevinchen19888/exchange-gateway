package com.alchemy.gateway.core.marketdata;

import com.alchemy.gateway.core.common.CandleInterval;
import com.alchemy.gateway.core.common.CoinPair;

import java.util.List;

public class DataSubscription {
    CoinPair coinPair;
    List<CandleInterval> intervals;
    boolean trades;
    boolean orderBook;
    MarketDataReceiver dataReceiver;

    /**
     * 关闭订阅
     */
    public void close() {
        // TODO: 实现关闭订阅的行为
    }
}
