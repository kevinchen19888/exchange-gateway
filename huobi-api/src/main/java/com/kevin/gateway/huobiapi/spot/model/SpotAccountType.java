package com.kevin.gateway.huobiapi.spot.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SpotAccountType {
    SPOT("spot"),
    MARGIN("margin"),
    OTC("otc"),
    POINT("point"),
    SUPER_MARGIN("super-margin"),
    INVESTMENT("investment"),
    BORROW("borrow");

    SpotAccountType(String type) {
        this.type = type;
    }

    private final String type;

    @JsonValue
    public String getType() {
        return type;
    }

    @JsonCreator
    public static SpotAccountType fromOf(String type) {
        for (SpotAccountType accountType : SpotAccountType.values()) {
            if (accountType.type.equals(type)) {
                return accountType;
            }
        }
        throw new IllegalArgumentException("无效火币账户类型");
    }
}
