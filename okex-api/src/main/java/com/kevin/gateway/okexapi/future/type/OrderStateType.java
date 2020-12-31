package com.kevin.gateway.okexapi.future.type;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 订单状态
 * -2：失败
 * -1：撤单成功
 * 0：等待成交
 * 1：部分成交
 * 2：完全成交
 * 3：下单中
 * 4：撤单中
 */
public enum OrderStateType {
    FAIL(-2, "失败"),
    WITHDRAW_OK(-1, "撤单成功"),
    WAIT_DEAL(0, "等待成交"),
    PATIAL_DEAL(1, "部分成交"),
    FULL_DEAL(2, "完全成交"),
    ORDERING(3, "下单中"),
    CANCELLING(4, "撤单中");

    private final Integer value;

    private final String name;

    OrderStateType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    @JsonCreator
    public static OrderStateType fromValue(String value) {
        for (OrderStateType type : OrderStateType.values()) {
            if (type.value.equals(Integer.valueOf(value))) {
                return type;
            }
        }
        throw new IllegalArgumentException("OrderStateType 无效 ,value= " + value);
    }

    @JsonValue
    public Integer getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }


}
