package com.kevin.gateway.huobiapi.spot.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SpotMarketStatus {
    NORMAL(1),
    HALTED(2),
    CANCEL_ONLY(3);

    SpotMarketStatus(Integer value) {
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
    public static SpotMarketStatus valueOf(int value) {
        for (SpotMarketStatus type : SpotMarketStatus.values()) {
            if (type.value == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("无效火币现货市场状态");
    }
}
