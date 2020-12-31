package com.kevin.gateway.okexapi.future.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


/**
 * 是否自动追加保证金
 * 1: 自动追加已开启
 * 0: 自动追加未开启
 */
public enum AutoMarginStatus {

    OPEN(1),
    CLOSE(0);

    private final Integer value;

    AutoMarginStatus(Integer value) {
        this.value = value;
    }

    @JsonCreator
    public static AutoMarginStatus fromValue(String v) {
        for (AutoMarginStatus type : AutoMarginStatus.values()) {
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
