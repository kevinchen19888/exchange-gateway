package com.kevin.gateway.okexapi.spot.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SpotAlgoOrderMode {
    SPOT(1),
    MARGIN(2);

    SpotAlgoOrderMode(int value) {
        this.intValue = value;
    }

    private final int intValue;

    @JsonValue
    public int getIntValue() {
        return this.intValue;
    }

    @JsonCreator
    public static SpotAlgoOrderMode valueOf(int value) {
        for (SpotAlgoOrderMode mode : SpotAlgoOrderMode.values()) {
            if (mode.intValue == value) {
                return mode;
            }
        }
        throw new IllegalArgumentException("无效委托订单model类型");
    }
}
