package com.kevin.gateway.okexapi.spot.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.lang.NonNull;

public enum SpotTransactionExecType {
    Maker("M"),
    Taker("T");

    SpotTransactionExecType(String symbol) {
        this.symbol = symbol;
    }

    private final String symbol;

    @JsonValue
    public String getSymbol() {
        return symbol;
    }

    @JsonCreator
    public static SpotTransactionExecType fromSymbol(@NonNull String symbol) {
        for (SpotTransactionExecType type : SpotTransactionExecType.values()) {
            if (type.getSymbol().equals(symbol))
                return type;
        }
        throw new IllegalArgumentException("无效的现货订单流动性方向");
    }
}
