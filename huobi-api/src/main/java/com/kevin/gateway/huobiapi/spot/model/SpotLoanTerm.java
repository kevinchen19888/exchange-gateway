package com.kevin.gateway.huobiapi.spot.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SpotLoanTerm {
    //借币期限（单位：天；有效值：10, 20, 30）
    TEN(10),
    TWENTY(20),
    THIRTY(30);

    SpotLoanTerm(int day) {
        this.day = day;
    }

    private final int day;

    @JsonValue
    public int getState() {
        return day;
    }

    @JsonCreator
    public static SpotLoanTerm fromOf(int day) {
        for (SpotLoanTerm type : SpotLoanTerm.values()) {
            if (type.day == day) {
                return type;
            }
        }
        throw new IllegalArgumentException("无效火币借币期限");
    }
}
