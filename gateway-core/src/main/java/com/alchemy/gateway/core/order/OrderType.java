package com.alchemy.gateway.core.order;

public enum OrderType {
    MARKET(0),
    LIMIT(1),
    STOP_LIMIT(2);

    OrderType(Integer value) {
        this.value = value;
    }

    private final int value;

    public String getName() {
        return this.name().toLowerCase();
    }

    public int getIntValue() {
        return value;
    }

    public static OrderType valueOf(int value) {
        for (OrderType orderType : OrderType.values()) {
            if (orderType.value == value) {
                return orderType;
            }
        }
        throw new IllegalArgumentException("无效订单交易状态");
    }
}
