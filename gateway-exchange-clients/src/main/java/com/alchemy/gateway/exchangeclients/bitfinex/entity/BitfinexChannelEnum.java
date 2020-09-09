package com.alchemy.gateway.exchangeclients.bitfinex.entity;

/**

 *
 *         {"event":"subscribed","channel":"candles","chanId":515,"key":"trade:14d:tBTCUSD"}
 *         {"event":"subscribed","channel":"trades","chanId":89,"symbol":"tBTCUSD","pair":"BTCUSD"}
 *         {"event":"subscribed","channel":"ticker","chanId":517,"symbol":"tBTCUSD","pair":"BTCUSD"}
 *         {"event":"subscribed","channel":"book","chanId":319,"symbol":"tBTCUSD","prec":"P0","freq":"F0","len":"25","pair":"BTCUSD"}
 *
 */
public enum  BitfinexChannelEnum {

    /**
     * candles
     */
    CANDLES,

    /**
     * trades
     */
    TRADES,

    /**
     * ticker
     */
    TICKER,

    /**
     * depth ,book
     */
    BOOK

}
