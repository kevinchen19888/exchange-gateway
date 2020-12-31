package com.kevin.gateway.okexapi.option.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum OptionOrderType {
    /**
     * 普通委托
     */
    NORMAL_LIMIT_ORDER(0);

    OptionOrderType(Integer value) {
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
    public static OptionOrderType valueOf(int value) {
        for (OptionOrderType orderType : OptionOrderType.values()) {
            if (orderType.value == value) {
                return orderType;
            }
        }
        throw new IllegalArgumentException("无效订单交易状态");
    }
}
