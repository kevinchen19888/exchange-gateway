package com.kevin.gateway.huobiapi.spot.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SpotC2cSide {
    LEND("lend"),
    BORROW("borrow");

    SpotC2cSide(String value) {
        this.val = value;
    }

    private final String val;

    @JsonValue
    public String getVal() {
        return this.val;
    }

    @JsonCreator
    public static SpotC2cSide fromVal(String value) {
        for (SpotC2cSide orderSide : SpotC2cSide.values()) {
            if (orderSide.val.equals(value)) {
                return orderSide;
            }
        }
        throw new IllegalArgumentException("无效借入借出订单方向");
    }
}
