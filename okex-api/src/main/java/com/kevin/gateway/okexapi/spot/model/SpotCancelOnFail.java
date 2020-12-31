package com.kevin.gateway.okexapi.spot.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SpotCancelOnFail {
    NOT_AUTOMATIC(0),
    AUTOMATIC(1);

    SpotCancelOnFail(int value) {
        this.intValue = value;
    }

    private final int intValue;

    @JsonValue
    public int getIntValue() {
        return this.intValue;
    }

    @JsonCreator
    public static SpotCancelOnFail valueOf(int value) {
        for (SpotCancelOnFail item : SpotCancelOnFail.values()) {
            if (item.intValue == value) {
                return item;
            }
        }
        throw new IllegalArgumentException("无效的现货自动撤销类型");
    }
}
