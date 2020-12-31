package com.kevin.gateway.huobiapi.spot.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SpotSubUserAccountType {
    //spot, isolated-margin, cross-margin, futures,swap
    ISOLATED_MARGIN("isolated-margin"),
    CROSS_MARGIN("cross-margin"),
    SPOT("spot"),
    FUTURES("futures"),
    SWAP("swap");

    SpotSubUserAccountType(String state) {
        this.state = state;
    }

    private final String state;

    @JsonValue
    public String getState() {
        return state;
    }

    @JsonCreator
    public static SpotSubUserAccountType fromOf(String state) {
        for (SpotSubUserAccountType type : SpotSubUserAccountType.values()) {
            if (type.state.equals(state)) {
                return type;
            }
        }
        throw new IllegalArgumentException("无效火币子用户账户类型");
    }
}
