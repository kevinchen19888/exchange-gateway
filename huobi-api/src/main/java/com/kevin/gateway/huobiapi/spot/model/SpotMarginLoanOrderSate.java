package com.kevin.gateway.huobiapi.spot.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SpotMarginLoanOrderSate {
    //created 未放款，accrual 已放款，cleared 已还清，invalid 异常

    CREATED("created"),
    ACCRUAL("accrual"),
    CLEARED("cleared"),
    INVALID("invalid");

    SpotMarginLoanOrderSate(String state) {
        this.state = state;
    }

    private final String state;

    @JsonValue
    public String getState() {
        return state;
    }

    @JsonCreator
    public static SpotMarginLoanOrderSate fromOf(String state) {
        for (SpotMarginLoanOrderSate type : SpotMarginLoanOrderSate.values()) {
            if (type.state.equals(state)) {
                return type;
            }
        }
        throw new IllegalArgumentException("无效火币借币订单状态");
    }
}
