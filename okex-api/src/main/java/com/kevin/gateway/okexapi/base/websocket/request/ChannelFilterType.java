package com.kevin.gateway.okexapi.base.websocket.request;

public enum ChannelFilterType {
    COIN,
    INSTRUMENT_ID,
    COIN_OR_UNDERLYING, // 分为币本位和金本位情况，比如：future/account:BTC 或者 BTC-USDT(目前只有交割合约支持)
    INDEX,// 标的指数
    INDEX_OR_COIN,// 标的指数 or instrument
    NONE    //   频道没有 filter，比如：future/instruments
}
