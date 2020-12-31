package com.kevin.gateway.huobiapi.spot.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ApiTrading {
    ENABLED("enabled"),
    DISABLED("disabled");

    ApiTrading(String state) {
        this.state = state;
    }

    private final String state;

    @JsonValue
    public String getState() {
        return state;
    }

    @JsonCreator
    public static ApiTrading fromOf(String state) {
        for (ApiTrading type : ApiTrading.values()) {
            if (type.state.equals(state)) {
                return type;
            }
        }
        throw new IllegalArgumentException("无效火币API交易使能标记");
    }
}
