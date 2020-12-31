package com.kevin.gateway.okexapi.future.common.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 本周 this_week
 * 次周 next_week
 * 季度 quarter
 * 次季度 bi_quarter
 */
public enum FutureAliasType {

    THIS_WEEK("this_week"),
    NEXT_WEEK("next_week"),
    QUARTER("quarter"),
    BI_QUARTER("bi_quarter");

    private final String value;


    FutureAliasType(String value) {
        this.value = value;
    }

    @JsonCreator
    public static FutureAliasType fromValue(String v) {
        for (FutureAliasType type : FutureAliasType.values()) {
            if (type.value.equals(v)) {
                return type;
            }
        }
        throw new IllegalArgumentException("FutureAliasType 无效 ,value= " + v);
    }

    @JsonValue
    public String getValue() {
        return this.value;
    }

}
