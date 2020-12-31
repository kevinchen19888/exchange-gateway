package com.kevin.gateway.okexapi.future.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


/**
 * 1:限价 2:市场价；止盈触发价格类型，默认是限价；为市场价时，委托价格不必填
 */

public enum TriggerType {

    LIMIT(1, "限价"),
    MARKET(2, "市场价");

    /**
     * 枚举值
     */
    private Integer value;

    /**
     * 描述
     */
    private String desc;

    TriggerType(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @JsonCreator
    public static TriggerType fromValue(Integer value) {
        for (TriggerType type : TriggerType.values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("TriggerType 无效 ,value= " + value);
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
