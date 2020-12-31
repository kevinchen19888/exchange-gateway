package com.kevin.gateway.okexapi.future.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


/**
 * 1.开多
 * 2.开空
 * 3.平多
 * 4.平空
 */
public enum OpenCloseLongShortType {
    OPEN_LONG(1, "开多"),
    OPEN_SHORT(2, "开空"),
    CLOSE_LONG(3, "平多"),
    CLOSE_SHORT(4, "平空");

    private final Integer value;

    private final String name;

    OpenCloseLongShortType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    @JsonCreator
    public static OpenCloseLongShortType fromValue(Integer value) {
        for (OpenCloseLongShortType type : OpenCloseLongShortType.values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("OpenCloseLongShortType 无效 ,value= " + value);
    }

    @JsonValue
    public String getValue() {
        return this.value.toString();
    }

    public String getName() {
        return this.name;
    }
}
