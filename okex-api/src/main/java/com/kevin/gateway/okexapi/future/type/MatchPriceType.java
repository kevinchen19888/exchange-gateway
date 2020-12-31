package com.kevin.gateway.okexapi.future.type;


import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 是否以对手价下单(0:不是; 1:是)，默认为0，当取值为1时，price字段无效。当以对手价下单，order_type只能选择0（普通委托）
 */

public enum MatchPriceType {
    MATCH(1),
    NOT_MATCH(0);

    private final Integer value;


    MatchPriceType(Integer value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return this.value.toString();
    }
}
