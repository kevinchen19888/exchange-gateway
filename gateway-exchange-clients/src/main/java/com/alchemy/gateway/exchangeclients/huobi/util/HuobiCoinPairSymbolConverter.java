package com.alchemy.gateway.exchangeclients.huobi.util;

import com.alchemy.gateway.core.common.CoinPair;
import com.alchemy.gateway.core.common.CoinPairSymbolConverter;

import java.util.Arrays;
import java.util.List;

/**
 * huobi symbol表示:btcusdt
 *
 * @author kevin chen
 */
public class HuobiCoinPairSymbolConverter implements CoinPairSymbolConverter {
    private static final List<String> SUPPORTED_BUYER_COINS = Arrays.asList("eth", "ht", "btc", "usdt", "husd", "trx");

    @Override
    public String coinPairToSymbol(CoinPair coinPair) {
        String symbol = coinPair.toSymbol(); // BTC/USDT
        return symbol.replace("/", "").toLowerCase();
    }

    @Override
    public CoinPair symbolToCoinPair(String symbol) {
        for (String buyerCoin : SUPPORTED_BUYER_COINS) {
            if (symbol.endsWith(buyerCoin)) {
                String[] coinArr = symbol.split(buyerCoin);
                String sellerCoin = coinArr[0];
                return new CoinPair(sellerCoin.toUpperCase(), buyerCoin.toUpperCase());
            }
        }
        return null;
    }
}
