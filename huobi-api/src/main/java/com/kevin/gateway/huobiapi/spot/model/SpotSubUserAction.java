package com.kevin.gateway.huobiapi.spot.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SpotSubUserAction {
    //lock(冻结)，unlock(解冻)
    LOCK("lock"),
    UNLOCK("unlock");

    SpotSubUserAction(String state) {
        this.state = state;
    }

    private final String state;

    @JsonValue
    public String getState() {
        return state;
    }

    @JsonCreator
    public static SpotSubUserAction fromOf(String state) {
        for (SpotSubUserAction type : SpotSubUserAction.values()) {
            if (type.state.equals(state)) {
                return type;
            }
        }
        throw new IllegalArgumentException("无效火币操作类型");
    }
}
