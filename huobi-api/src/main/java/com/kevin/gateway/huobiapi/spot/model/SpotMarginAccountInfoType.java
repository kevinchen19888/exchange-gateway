package com.kevin.gateway.huobiapi.spot.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SpotMarginAccountInfoType {
    //类型	trade: 交易余额,
    // frozen: 冻结余额,
    // loan: 待还借贷本金,
    // interest: 待还借贷利息,
    // transfer-out-available,loan-available://可借btc,只有传入symbol才会返回
    TRADE("trade"),
    FROZEN("frozen"),
    LOAN("loan"),
    INTEREST("interest"),
    TRANSFER_OUT_AVAILABLE("transfer-out-available"),
    LOAN_AVAILABLE("loan-available");

    SpotMarginAccountInfoType(String state) {
        this.state = state;
    }

    private final String state;

    @JsonValue
    public String getState() {
        return state;
    }

    @JsonCreator
    public static SpotMarginAccountInfoType fromOf(String state) {
        for (SpotMarginAccountInfoType type : SpotMarginAccountInfoType.values()) {
            if (type.state.equals(state)) {
                return type;
            }
        }
        throw new IllegalArgumentException("无效火币借币账户详情类型");
    }
    
}
