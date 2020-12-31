package com.kevin.gateway.okexapi.swap.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 方向
 * 1:逐仓-多仓
 * 2:逐仓-空仓
 * 3:全仓
 */

public enum SwapSideType {

    FIX_LONG(1, "逐仓-多仓"),
    FIX_SHORT(2, "逐仓-空仓"),
    CROSSED(3, "全仓");

    private final Integer value;

    private final String name;

    SwapSideType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    @JsonCreator
    public static SwapSideType fromValue(Integer value) {
        for (SwapSideType type : SwapSideType.values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("SwapSideType 无效 ,value= " + value);
    }

    @JsonValue
    public Integer getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }

}
