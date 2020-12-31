package com.kevin.gateway.okexapi.future.type;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 订单状态
 * 1: 待生效
 * 2: 已生效
 * 3: 已撤销
 * 4: 部分生效
 * 5: 暂停生效
 */
public enum OrderStatusType {

    WAIT_VALID(1, "待生效"),
    VALID(2, "已生效"),
    CANCELLED(3, "已撤销"),
    PATIAL_VALID(4, "部分生效"),
    PAUSE_VALID(5, "暂停生效");

    private final Integer value;

    private final String name;

    OrderStatusType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    @JsonCreator
    public static OrderStatusType fromValue(String value) {
        for (OrderStatusType type : OrderStatusType.values()) {
            if (type.value.equals(Integer.valueOf(value))) {
                return type;
            }
        }
        throw new IllegalArgumentException("OrderStatusType 无效 ,value= " + value);
    }

    @JsonValue
    public Integer getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }


}
