package com.kevin.gateway.okexapi.base.util;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 深度数据（增量/全量）响应体中的行为枚举
 */
public enum DepthAction {
    /**
     * 部分更新
     */
    PARTIAL("partial"),

    /**
     * 全量更新
     */
    UPDATE("update");
    private final String value;

    DepthAction(String value) {
        this.value = value;
    }
    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static DepthAction fromName(final String value) {
        for (DepthAction depthAction : DepthAction.values()) {
            if (depthAction.value.equals(value)) return depthAction;
        }
        throw new IllegalArgumentException(String.format("不支持%S深度类型", value));

    }

}

