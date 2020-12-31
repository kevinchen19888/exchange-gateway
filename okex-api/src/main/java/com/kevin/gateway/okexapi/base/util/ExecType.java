package com.kevin.gateway.okexapi.base.util;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ExecType {
    TAKER("T"),
    MAKER("M");

    ExecType(String value) {
        this.val = value;
    }

    private final String val;

    @JsonValue
    public String getVal() {
        return this.val;
    }

    @JsonCreator
    public static ExecType fromVal(String value) {
        for (ExecType execType : ExecType.values()) {
            if (execType.val.equals(value)) {
                return execType;
            }
        }
        throw new IllegalArgumentException("无效订单交易类型,s:" + value);
    }
}
