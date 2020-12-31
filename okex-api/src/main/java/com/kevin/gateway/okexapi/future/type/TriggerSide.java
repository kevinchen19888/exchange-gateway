package com.kevin.gateway.okexapi.future.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


/**
 * 止盈止损触发方向：1:止盈 2:止损
 */
public enum TriggerSide {

    STOP_PROFIT(1, "止盈"),
    STOP_LOSS(2, "止损");

    /**
     * 枚举值
     */
    private Integer value;

    /**
     * 描述
     */
    private String desc;

    TriggerSide(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @JsonCreator
    public static TriggerSide fromValue(Integer value) {
        for (TriggerSide type : TriggerSide.values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("TriggerSide 无效 ,value= " + value);
    }

    @JsonValue
    public Integer getValue() {
        return this.value;
    }

    public String getName() {
        return this.desc;
    }

    @Override
    public String toString() {
        return this.value.toString();
    }

}
