package com.kevin.gateway.okexapi.option.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 期权类型，C或P
 */
public enum OptionType {
    C("C"),
    P("P");

    private final String val;

    OptionType(String val) {
        this.val = val;
    }

    @JsonValue
    public String getVal() {
        return val;
    }

    @JsonCreator
    public static OptionType fromVal(String value) {
        for (OptionType optionType : OptionType.values()) {
            if (optionType.val.equals(value)) {
                return optionType;
            }
        }
        throw new IllegalArgumentException("无效期权类型");
    }
}
