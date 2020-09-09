package com.alchemy.gateway.core.common;

import lombok.Data;

/**
 * 市场
 */
@Data
public final class Market {
    private final CoinPair coinPair; // 币对
    private final MarketType type; // 市场类型
    private final Integer pricePrecision;

    public static Market spotMarket(CoinPair coinPair) {
        return new Market(coinPair, MarketType.SPOT, 0);
    }

    public static Market spotMarket(CoinPair coinPair, Integer pricePrecision) {
        return new Market(coinPair, MarketType.SPOT, pricePrecision);
    }
}
