package com.alchemy.gateway.exchangeclients.binance.util;

import com.alchemy.gateway.core.common.CoinPair;
import com.alchemy.gateway.core.common.CoinPairSymbolConverter;

import java.util.Arrays;
import java.util.List;

/**
 * binance symbol表示:BTCUSDT
 *
 * @author kevin chen
 */
public class BinanceCoinPairSymbolConverter implements CoinPairSymbolConverter {
    private static final List<String> SUPPORTED_SELLER_COINS = Arrays.asList("EUR","PAX","USDT","USDS","ZAR","BIDR","DAI","BTC","AUD","BKRW","NGN","GBP","TUSD","BNB","XRP","ETH","USDC","TRY","TRX","BUSD","UAH","RUB","IDRT");

    @Override
    public String coinPairToSymbol(CoinPair coinPair) {
        if (coinPair == null) {
            throw new IllegalArgumentException("SymbolConverter need coinPair");
        }
        String symbol = coinPair.toSymbol(); // BTC/USDT
        String[] binanceSymbols = symbol.split("/");
        return binanceSymbols[0] + binanceSymbols[1];
    }

    @Override
    public CoinPair symbolToCoinPair(String symbol) {

        for (String sellerCoin : SUPPORTED_SELLER_COINS) {
            if (symbol.startsWith(sellerCoin)) {
                String buyerCoin = symbol.substring(sellerCoin.length());
                return new CoinPair(sellerCoin, buyerCoin);
            }
        }
        return null;
    }
}
