package com.kevin.gateway.okexapi.future.type;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum LastAmendResult {
    FAILED(-1, "失败"),
    SUCCESS(0, "成功"),
    AUTO_CANCEL(1, "自动撤单");

    private final int value;
    private final String name;

    LastAmendResult(int value, String name) {
        this.value = value;
        this.name = name;
    }

    @JsonCreator
    public static LastAmendResult fromValue(int value) {
        for (LastAmendResult olar : LastAmendResult.values()) {
            if (olar.value == value) return olar;
        }
        throw new IllegalArgumentException(String.format("不支持%d类型", value));
    }

    public String getName() {
        return this.name;
    }
}
