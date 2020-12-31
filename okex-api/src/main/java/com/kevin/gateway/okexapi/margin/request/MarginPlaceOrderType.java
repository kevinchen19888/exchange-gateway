package com.kevin.gateway.okexapi.margin.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum MarginPlaceOrderType {
    NORMAL_ORDER(0),
    POST_ONLY(1),
    FILL_OR_KILL(2),
    IMMEDIATE_OR_CANCEL(3);

    MarginPlaceOrderType(Integer value) {
        this.value = value;
    }

    private final int value;

    public String getName() {
        return this.name().toLowerCase();
    }

    @JsonValue
    public int getIntValue() {
        return value;
    }

    @JsonCreator
    public static MarginPlaceOrderType valueOf(int value) {
        for (MarginPlaceOrderType spotPlaceOrderType : MarginPlaceOrderType.values()) {
            if (spotPlaceOrderType.value == value) {
                return spotPlaceOrderType;
            }
        }
        throw new IllegalArgumentException("无效币币杠杆订单类型");
    }
}
