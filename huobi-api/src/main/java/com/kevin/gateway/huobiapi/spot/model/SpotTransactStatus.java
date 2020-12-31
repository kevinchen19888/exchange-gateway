package com.kevin.gateway.huobiapi.spot.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SpotTransactStatus {
    PENDING("pending"),
    CLOSED("closed");

    SpotTransactStatus(String state) {
        this.state = state;
    }

    private final String state;

    @JsonValue
    public String getState() {
        return state;
    }

    @JsonCreator
    public static SpotTransactStatus fromOf(String state) {
        for (SpotTransactStatus type : SpotTransactStatus.values()) {
            if (type.state.equals(state)) {
                return type;
            }
        }
        throw new IllegalArgumentException("无效火币还币状态");
    }
}
