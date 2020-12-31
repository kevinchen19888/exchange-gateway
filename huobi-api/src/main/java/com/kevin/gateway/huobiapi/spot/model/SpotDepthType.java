package com.kevin.gateway.huobiapi.spot.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SpotDepthType {
    /*step0 无聚合
    step1 聚合度为报价精度*10
    step2 聚合度为报价精度*100
    step3 聚合度为报价精度*1000
    step4 聚合度为报价精度*10000
    step5 聚合度为报价精度*100000*/

    step0("step0"),
    step1("step1"),
    step2("step2"),
    step3("step3"),
    step4("step4"),
    step5("step5");

    SpotDepthType(String type) {
        this.type = type;
    }

    private final String type;

    @JsonValue
    public String getType() {
        return type;
    }

    @JsonCreator
    public static SpotDepthType fromOf(String type) {
        for (SpotDepthType result : SpotDepthType.values()) {
            if (result.type.equals(type)) {
                return result;
            }
        }
        throw new IllegalArgumentException("无效火币现货深度的价格聚合度");
    }

}
