package com.kevin.gateway.okexapi.base.util;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum  AlgoOrderType {
    TRIGGER(1),
    TRAIL(2),
    ICEBERG(3),
    TWAP(4),
    STOP(5);

    private int value;

    AlgoOrderType(int value) {
        this.value = value;
    }

    @JsonCreator
    public static AlgoOrderType fromValue(int value){
        for (AlgoOrderType aot : AlgoOrderType.values()) {
            if (aot.value == value) return aot;
        }
        throw new IllegalArgumentException(String.format("不支持%S订单类型", value));
    }

}
