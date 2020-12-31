package com.kevin.gateway.okexapi.future.type;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 开/关自动增加保证金：1、开 2、关
 */
public enum AutoMarginType {

    OPEN(1),
    CLOSE(2);

    private final Integer value;

    AutoMarginType(Integer value) {
        this.value = value;
    }

    @JsonCreator
    public static AutoMarginType fromValue(String v) {
        for (AutoMarginType type : AutoMarginType.values()) {
            if (type.value.equals(Integer.valueOf(v))) {
                return type;
            }
        }
        throw new IllegalArgumentException("AutoMarginType 无效 ,value= " + v);
    }

    @JsonValue
    public String getValue() {
        return this.value.toString();
    }
}
