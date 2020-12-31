package com.kevin.gateway.okexapi.spot.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SpotAccountTradeFeeCategory {
    ONE(1),
    TWO(2),
    THERE(3),
    FOUR(4);

    SpotAccountTradeFeeCategory(int value) {
        this.intValue = value;
    }

    private final int intValue;

    @JsonValue
    public int getIntValue() {
        return this.intValue;
    }

    @JsonCreator
    public static SpotAccountTradeFeeCategory valueOf(int value) {
        for (SpotAccountTradeFeeCategory category : SpotAccountTradeFeeCategory.values()) {
            if (category.intValue == value) {
                return category;
            }
        }
        throw new IllegalArgumentException("无效现货手续费档位"+value);
    }
}
