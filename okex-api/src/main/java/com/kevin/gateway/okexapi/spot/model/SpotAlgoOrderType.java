package com.kevin.gateway.okexapi.spot.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SpotAlgoOrderType {
    TRIGGER_ORDER(1),
    TRAIL_ORDER(2),
    ICEBERG_ORDER(3),
    TIME_WEIGHTED_AVERAGE_PRICE(4),
    STOP_ORDER(5);


    SpotAlgoOrderType(Integer value) {
        this.value = value;
    }

    private final int value;

    public String getName() {
        return this.name().toLowerCase();
    }

    @JsonValue
    public int getIntValue() {
        return value;
    }

    @JsonCreator
    public static SpotAlgoOrderType valueOf(int value) {
        for (SpotAlgoOrderType type : SpotAlgoOrderType.values()) {
            if (type.value == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("无效现货委托单类型");
    }
}
