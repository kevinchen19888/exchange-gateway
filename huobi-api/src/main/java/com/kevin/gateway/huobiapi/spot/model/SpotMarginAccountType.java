package com.kevin.gateway.huobiapi.spot.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SpotMarginAccountType {
    MARGIN("margin"),
    CROSS_MARGIN("cross-margin");

    SpotMarginAccountType(String state) {
        this.state = state;
    }

    private final String state;

    @JsonValue
    public String getState() {
        return state;
    }

    @JsonCreator
    public static SpotMarginAccountType fromOf(String state) {
        for (SpotMarginAccountType type : SpotMarginAccountType.values()) {
            if (type.state.equals(state)) {
                return type;
            }
        }
        throw new IllegalArgumentException("无效火币借币账户类型");
    }
}
