package com.alchemy.gateway.exchangeclients.okex.impl;

import com.alchemy.gateway.core.common.CoinPair;
import com.alchemy.gateway.core.common.CoinPairSymbolConverter;

public class OkexCoinPairSymbolConverter implements CoinPairSymbolConverter {

    @Override
    public String coinPairToSymbol(CoinPair coinPair) {
        return coinPair.getSellCoin().toUpperCase() + '-' + coinPair.getBuyCoin().toUpperCase();
    }

    @Override
    public CoinPair symbolToCoinPair(String symbol) {
        String[] items = symbol.split("-");
        return new CoinPair(items[0],items[1] );
    }
}
