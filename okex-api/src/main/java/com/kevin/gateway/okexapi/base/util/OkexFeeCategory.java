package com.kevin.gateway.okexapi.base.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 手续费档位
 */
public enum OkexFeeCategory {
    /**
     * 第一档
     */
    LEVEL_1(1),
    /**
     * 第二档
     */
    LEVEL_2(2),
    /**
     * 第三档
     */
    LEVEL_3(3),
    /**
     * 第4档
     */
    LEVEL_4(4);

    private final int val;

    OkexFeeCategory(int val) {
        this.val = val;
    }

    @JsonValue
    public int getVal() {
        return val;
    }

    @JsonCreator
    public static OkexFeeCategory fromValue(int value) {
        for (OkexFeeCategory category : OkexFeeCategory.values()) {
            if (category.val == value) return category;
        }
        throw new IllegalArgumentException(String.format("不支持的手续费档位:%S", value));
    }
}
