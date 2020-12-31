package com.kevin.gateway.huobiapi.spot.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum BalanceType {
    //trade: 交易余额，frozen: 冻结余额
    TRADE("trade"),
    FROZEN("frozen");

    BalanceType(String type) {
        this.type = type;
    }

    private final String type;

    @JsonValue
    public String getType() {
        return type;
    }

    @JsonCreator
    public static BalanceType fromOf(String type) {
        for (BalanceType balanceType : BalanceType.values()) {
            if (balanceType.type.equals(type)) {
                return balanceType;
            }
        }
        throw new IllegalArgumentException("无效火币余额类型");
    }
}
