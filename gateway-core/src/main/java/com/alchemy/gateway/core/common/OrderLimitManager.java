package com.alchemy.gateway.core.common;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author kevin chen
 */
public class OrderLimitManager {
    private final Map<String, OrderLimit> orderLimitMap = new ConcurrentHashMap<>();

    /**
     * 返回对应交易所的某个市场的OrderLimit
     *
     * @param key exchangeName+symbol(平台内部symbol表示[BTC/USDT]与具体交易所无关)
     * @return OrderLimit 可能为null
     */
    public OrderLimit getOrderLimit(final String key) {
        return orderLimitMap.get(key);
    }

    /**
     * 添加交易所市场的OrderLimit
     *
     * @param exchangeName 交易所名称
     * @param orderLimit   交易所市场的OrderLimit,不能为null
     */
    public void addOrderLimit(String exchangeName, OrderLimit orderLimit) {
        orderLimitMap.putIfAbsent(exchangeName + orderLimit.getSymbol(), orderLimit);
    }

}
