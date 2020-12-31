package com.kevin.gateway.huobiapi.spot.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SpotSubAccountType {
    //账户子类型（trade, lending, earnings, loan, interest, advance）
    trade("trade"),
    lending("lending"),
    earnings("earnings"),
    loan("loan"),
    interest("interest"),
    advance("advance");

    SpotSubAccountType(String state) {
        this.state = state;
    }

    private final String state;

    @JsonValue
    public String getState() {
        return state;
    }

    @JsonCreator
    public static SpotSubAccountType fromOf(String state) {
        for (SpotSubAccountType type : SpotSubAccountType.values()) {
            if (type.state.equals(state)) {
                return type;
            }
        }
        throw new IllegalArgumentException("无效火币C2C子用户账户类型");
    }
}
