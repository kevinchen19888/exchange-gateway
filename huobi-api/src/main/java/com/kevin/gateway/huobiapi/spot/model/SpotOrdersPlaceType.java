package com.kevin.gateway.huobiapi.spot.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SpotOrdersPlaceType {
    BUY_MARKET("buy-market"),//市价买
    SELL_MARKET("sell-market"),//市价卖
    BUY_LIMIT("buy-limit"),//限价买
    SELL_LIMIT("sell-limit"),//限价卖
    BUY_IOC("buy-ioc"),//IOC(立即成交或取消)买单
    SELL_IOC("sell-ioc"),//IOC(立即成交或取消)卖单
    BUY_LIMIT_MAKER(" buy-limit-maker"),
    SELL_LIMIT_MAKER("sell-limit-maker"),
    BUY_STOP_LIMIT("buy-stop-limit"),//止盈止损买单
    SELL_STOP_LIMIT("sell-stop-limit"),//止盈止损卖单
    BUY_LIMIT_FOK("buy-limit-fok"),//fok限价买单
    SELL_LIMIT_FOK("sell-limit-fok"),//fok限价买单
    BUY_STOP_LIMIT_FOK("buy-stop-limit-fo"),//fok止盈止损买单
    SELL_STOP_LIMIT_FOK("sell-stop-limit-fok");//for止盈止损卖单

    SpotOrdersPlaceType(String state) {
        this.state = state;
    }

    private final String state;

    @JsonValue
    public String getState() {
        return state;
    }

    @JsonCreator
    public static SpotOrdersPlaceType fromOf(String state) {
        for (SpotOrdersPlaceType type : SpotOrdersPlaceType.values()) {
            if (type.state.equals(state)) {
                return type;
            }
        }
        throw new IllegalArgumentException("无效火币订单类型");
    }
}
