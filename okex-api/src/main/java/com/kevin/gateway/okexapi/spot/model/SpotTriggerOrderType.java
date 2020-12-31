package com.kevin.gateway.okexapi.spot.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum SpotTriggerOrderType {

    limit(0),
    market(1);

    private final int type;

    SpotTriggerOrderType(int value) {
        this.type = value;
    }

    @JsonCreator
    public static SpotTriggerOrderType fromValue(int value) {
        for (SpotTriggerOrderType oscc : SpotTriggerOrderType.values()) {
            if (oscc.type == value ) return oscc;
        }
        throw new IllegalArgumentException(String.format("计划委托不支持%d类型", value));
    }


}
