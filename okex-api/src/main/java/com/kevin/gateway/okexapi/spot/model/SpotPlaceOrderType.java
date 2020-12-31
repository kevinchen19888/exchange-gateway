package com.kevin.gateway.okexapi.spot.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SpotPlaceOrderType {
    NORMAL_ORDER(0),
    POST_ONLY(1),
    FILL_OR_KILL(2),
    IMMEDIATE_OR_CANCEL(3);

    SpotPlaceOrderType(Integer value) {
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
    public static SpotPlaceOrderType valueOf(int value) {
        for (SpotPlaceOrderType spotPlaceOrderType : SpotPlaceOrderType.values()) {
            if (spotPlaceOrderType.value == value) {
                return spotPlaceOrderType;
            }
        }
        throw new IllegalArgumentException("无效现货订单类型");
    }
}
