package com.kevin.gateway.okexapi.base.util;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum OrderSide {
    BUY("buy"),
    SELL("sell");

    OrderSide(String value) {
        this.val = value;
    }

    private final String val;

    @JsonValue
    public String getVal() {
        return this.val;
    }

    @JsonCreator
    public static OrderSide fromVal(String value) {
        for (OrderSide orderSide : OrderSide.values()) {
            if (orderSide.val.equals(value)) {
                return orderSide;
            }
        }
        throw new IllegalArgumentException("无效订单交易方向状态");
    }
}
