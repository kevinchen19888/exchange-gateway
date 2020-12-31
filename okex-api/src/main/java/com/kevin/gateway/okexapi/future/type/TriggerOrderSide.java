package com.kevin.gateway.okexapi.future.type;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum TriggerOrderSide {
    STOP_PROFIT(1, "止盈"),
    STOP_LOSS(2, "止损"),
    BIDIRECTIONAL(3, "双向");

    private final int value;
    private final String name;
    TriggerOrderSide(int value, String name) {
        this.value = value;
        this.name = name;
    }

    @JsonCreator
    public static TriggerOrderSide valueOf(int value) {
        for (TriggerOrderSide side : TriggerOrderSide.values()) {
            if (side.value == value) {
                return side;
            }
        }
        throw new IllegalArgumentException(String.format("不支持%d类型", value));
    }

    public String getName() {
        return this.name;
    }
}
