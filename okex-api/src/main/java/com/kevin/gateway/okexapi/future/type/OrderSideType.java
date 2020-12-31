package com.kevin.gateway.okexapi.future.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 下单委托方向：1:止盈 2:止损, 3双向
 */
public enum OrderSideType {

    STOP_PROFIT(1, "止盈"),
    STOP_LOSS(2, "止损"),
    STOP_BI(3, "双向");

    /**
     * 枚举值
     */
    private final Integer value;

    /**
     * 描述
     */
    private final String desc;

    OrderSideType(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @JsonCreator
    public static OrderSideType fromValue(Integer value) {
        for (OrderSideType type : OrderSideType.values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("OrderSideType 无效 ,value= " + value);
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
