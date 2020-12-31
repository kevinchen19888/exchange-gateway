package com.kevin.gateway.okexapi.future.type;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 平仓方向
 * long:平多
 * short:平空
 */
public enum DirectionType {

    LONG("long"),
    SHORT("short");

    private final String value;

    DirectionType(String desc) {
        this.value = desc;
    }

    @JsonCreator
    public static DirectionType fromValue(String value) {
        for (DirectionType type : DirectionType.values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("AutoMarginType 无效 ,value= " + value);
    }

    @JsonValue
    public String getValue() {
        return this.value;

    }
}
