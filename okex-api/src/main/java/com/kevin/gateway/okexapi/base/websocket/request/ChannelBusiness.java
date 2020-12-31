package com.kevin.gateway.okexapi.base.websocket.request;

import com.kevin.gateway.core.Symbolic;

public enum ChannelBusiness implements Symbolic {
    SPOT("spot"),
    FUTURE("futures"),
    SWAP("swap"),
    OPTION("option"),
    INDEX("index");

    private final String symbol;

    ChannelBusiness(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String getSymbol() {
        return this.symbol;
    }

    public static ChannelBusiness fromSymbol(String symbol) {
        for (ChannelBusiness cb : ChannelBusiness.values()) {
            if (cb.symbol.equals(symbol)) {
                return cb;
            }
        }
        throw new IllegalArgumentException("非法的频道业务符号: " + symbol);
    }
}
