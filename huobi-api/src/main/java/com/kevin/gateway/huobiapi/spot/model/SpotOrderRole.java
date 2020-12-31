package com.kevin.gateway.huobiapi.spot.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SpotOrderRole {

    //成交角色	maker,taker

    MAKER("maker"),
    TAKER("taker");

    SpotOrderRole(String state) {
        this.state = state;
    }

    private final String state;

    @JsonValue
    public String getState() {
        return state;
    }

    @JsonCreator
    public static SpotOrderRole fromOf(String state) {
        for (SpotOrderRole type : SpotOrderRole.values()) {
            if (type.state.equals(state)) {
                return type;
            }
        }
        throw new IllegalArgumentException("无效火币成交角色");
    }
}
