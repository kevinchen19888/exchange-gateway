package com.kevin.gateway.huobiapi.spot.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SpotDepositState {
    UNKNOWN("unknown"),	//状态未知
    CONFIRMING("confirming"),	//确认中
    CONFIRMED("confirmed"),	//已确认
    SAFE("safe"),	//已完成
    ORPHAN("orphan");	//待确认

    SpotDepositState(String state) {
        this.state = state;
    }

    private final String state;

    @JsonValue
    public String getState() {
        return state;
    }

    @JsonCreator
    public static SpotDepositState fromOf(String state) {
        for (SpotDepositState type : SpotDepositState.values()) {
            if (type.state.equals(state)) {
                return type;
            }
        }
        throw new IllegalArgumentException("无效火币充币状态");
    }
}
