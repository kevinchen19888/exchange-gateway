package com.kevin.gateway.okexapi.future.common.type;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 订单类型
 * 3:平多
 * 4:平空
 */
public enum LiquidationType {

    /**
     * 3:平多
     */
    LIQ_LONG(3),

    /**
     * 4:平空
     */
    LIQ_SHORT(4);

    private final Integer value;

    LiquidationType(Integer value) {
        this.value = value;
    }

    @JsonCreator
    public static LiquidationType fromValue(String v) {
        for (LiquidationType type : LiquidationType.values()) {
            if (type.value.equals(Integer.valueOf(v))) {
                return type;
            }
        }
        throw new IllegalArgumentException("AccoutFromToType 无效 ,value= " + v);
    }

    @JsonValue
    public Integer getValue() {
        return this.value;
    }
}
