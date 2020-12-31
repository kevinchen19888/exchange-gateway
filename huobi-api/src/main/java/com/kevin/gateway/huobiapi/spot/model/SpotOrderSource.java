package com.kevin.gateway.huobiapi.spot.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SpotOrderSource {
    //现货交易填写“spot-api”，逐仓杠杆交易填写“margin-api”，全仓杠杆交易填写“super-margin-api”, C2C杠杆交易填写"c2c-margin-api"
    //api,web,ios,android,mac,windows,sys

    SPOT_API("spot-api"),
    MARGIN_API("margin-api"),
    SUPER_MARGIN_API("super-margin-api"),
    C2C_MARGIN_API("c2c-margin-ap"),
    WEB("web"),
    API("api"),
    IOS("ios"),
    ANDROID("android"),
    MAC("mac"),
    WINDOWS("windows"),
    SYS("sys");

    SpotOrderSource(String state) {
        this.state = state;
    }

    private final String state;

    @JsonValue
    public String getState() {
        return state;
    }

    @JsonCreator
    public static SpotOrderSource fromOf(String state) {
        for (SpotOrderSource type : SpotOrderSource.values()) {
            if (type.state.equals(state)) {
                return type;
            }
        }
        throw new IllegalArgumentException("无效火币订单交易类型");
    }
}
