package com.kevin.gateway.huobiapi.spot.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SpotSubUserActivation {
    ACTIVATED("activated"),
    DEACTIVATED("deactivated");

    SpotSubUserActivation(String state) {
        this.state = state;
    }

    private final String state;

    @JsonValue
    public String getState() {
        return state;
    }

    @JsonCreator
    public static SpotSubUserActivation fromOf(String state) {
        for (SpotSubUserActivation type : SpotSubUserActivation.values()) {
            if (type.state.equals(state)) {
                return type;
            }
        }
        throw new IllegalArgumentException("无效火币子用户账户激活状态");
    }
}
