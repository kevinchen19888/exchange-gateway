package com.alchemy.gateway.core.order;


public enum OrderSide {
    BUY(0),
    SELL(1);

    OrderSide(int value) {
        this.intValue = value;
    }

    private final int intValue;

    public int getIntValue() {
        return this.intValue;
    }

    public static OrderSide valueOf(int value) {
        for (OrderSide orderSide : OrderSide.values()) {
            if (orderSide.intValue == value) {
                return orderSide;
            }
        }
        throw new IllegalArgumentException("无效订单交易方向状态");
    }
}
