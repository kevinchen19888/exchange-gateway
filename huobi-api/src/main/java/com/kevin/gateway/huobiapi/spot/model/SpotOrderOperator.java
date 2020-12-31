package com.kevin.gateway.huobiapi.spot.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SpotOrderOperator {
    //止盈止损订单触发价运算符 gte – greater than and equal (>=), lte – less than and equal (<=)

    GTE(">="),//市价买
    LTE("<=");

    SpotOrderOperator(String state) {
        this.state = state;
    }

    private final String state;

    @JsonValue
    public String getState() {
        return state;
    }

    @JsonCreator
    public static SpotOrderOperator fromOf(String state) {
        for (SpotOrderOperator type : SpotOrderOperator.values()) {
            if (type.state.equals(state)) {
                return type;
            }
        }
        throw new IllegalArgumentException("无效火币订单交易类型");
    }
}
