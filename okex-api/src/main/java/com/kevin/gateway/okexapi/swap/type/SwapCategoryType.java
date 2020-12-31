package com.kevin.gateway.okexapi.swap.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 手续费档位
 * 1:第一档
 * 2:第二档
 */

public enum SwapCategoryType {

    CATEGORY_TYPE_1(1, "第一档"),
    CATEGORY_TYPE_2(2, "第二档");

    private final Integer value;

    private final String name;

    SwapCategoryType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    @JsonCreator
    public static SwapCategoryType fromValue(Integer value) {
        for (SwapCategoryType type : SwapCategoryType.values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("SwapCategoryType 无效 ,value= " + value);
    }

    @JsonValue
    public Integer getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }

}
