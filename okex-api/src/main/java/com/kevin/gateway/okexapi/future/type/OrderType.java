package com.kevin.gateway.okexapi.future.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 0：普通委托（order type不填或填0都是普通委托）
 * 1：只做Maker（Post only）
 * 2：全部成交或立即取消（FOK）
 * 3：立即成交并取消剩余（IOC）
 * 4：市价委托
 */
public enum OrderType {
    COMMON(0, "普通委托（order type不填或填0都是普通委托）"),
    MAKER(1, "只做Maker（Post only）"),
    FOK(2, "全部成交或立即取消（FOK）"),
    IOC(3, "立即成交并取消剩余（IOC）"),
    MARKET(4, "市价委托");


    private final Integer value;

    private final String name;

    OrderType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    @JsonCreator
    public static OrderType fromValue(String value) {
        for (OrderType type : OrderType.values()) {
            if (type.value.equals(Integer.valueOf(value))) {
                return type;
            }
        }
        throw new IllegalArgumentException("OrderType 无效 ,value= " + value);
    }

    @JsonValue
    public String getValue() {
        return this.value.toString();
    }

    public String getName() {
        return this.name;
    }

}
