package com.kevin.gateway.huobiapi.spot.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SpotMarginAccountState {
    //账户状态	working,fl-sys,fl-mgt,fl-end,fl-negative,lock
    WORKING("working"),
    FL_SYS("fl-sys"),
    FL_MGT("fl-mgt"),
    FL_END("fl-end"),
    FL_NEGATIVE("fl-negative"),
    LOCK("lock");

    SpotMarginAccountState(String state) {
        this.state = state;
    }

    private final String state;

    @JsonValue
    public String getState() {
        return state;
    }

    @JsonCreator
    public static SpotMarginAccountState fromOf(String state) {
        for (SpotMarginAccountState type : SpotMarginAccountState.values()) {
            if (type.state.equals(state)) {
                return type;
            }
        }
        throw new IllegalArgumentException("无效火币借币账户状态");
    }
}
