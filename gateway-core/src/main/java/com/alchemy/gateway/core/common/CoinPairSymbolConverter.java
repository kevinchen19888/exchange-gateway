package com.alchemy.gateway.core.common;

/**
 * 币对与交易所标识相互转换器
 */
public interface CoinPairSymbolConverter {
    /**
     * 将币对转换为交易所币对符号
     *
     * @param coinPair 指定币对，比如：CoinPair("BTC", "USDT")
     * @return 交易所符号，比如：btcusdt
     */
    String coinPairToSymbol(CoinPair coinPair);

    /**
     * 将交易所币对符号转换为币对
     *
     * @param symbol 交易所符号，比如：btcusdt
     * @return 币对，比如：CoinPair("BTC", "USDT")
     */
    CoinPair symbolToCoinPair(String symbol);
}
