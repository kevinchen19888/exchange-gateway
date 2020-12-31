package com.kevin.gateway.okexapi.future.type;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum TriggerOrderType {

    limit(0),
    market(1);

    private final int type;

    TriggerOrderType(int value) {
        this.type = value;
    }

    @JsonCreator
    public static TriggerOrderType fromValue(int value) {
        for (TriggerOrderType oscc : TriggerOrderType.values()) {
            if (oscc.type == value) return oscc;
        }
        throw new IllegalArgumentException(String.format("计划委托不支持%d类型", value));
    }


}
