package com.kevin.gateway.okexapi.future.type;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 增加/减少：1：增加 2：减少
 */
public enum IncreaseDecreaseType {

    INCREASE(1),
    DECREASE(2);

    private final Integer value;

    IncreaseDecreaseType(int value) {
        this.value = value;
    }

    @JsonCreator
    public static IncreaseDecreaseType fromValue(Integer value) {
        for (IncreaseDecreaseType type : IncreaseDecreaseType.values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("AutoMarginType 无效 ,value= " + value);
    }

    @JsonValue
    public String getValue() {
        return this.value.toString();
    }
}
