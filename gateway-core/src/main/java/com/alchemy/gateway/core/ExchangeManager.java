package com.alchemy.gateway.core;

import java.util.Map;
import java.util.Set;

public interface ExchangeManager {
    /**
     * 获取支持的交易所名称集合，比如：binance, huobi, okex, ...
     *
     * @return 交易所名称集合
     */
    Set<String> getNames();

    /**
     * 根据名称查找交易所接口
     *
     * @param name 指定名称，比如：huobi, okex, ..
     * @return 交易所接口
     */
    ExchangeApi getAPI(String name);

    /**
     * 判断是否支持指定的交易所
     *
     * @param name 交易所名称，比如：huobi, okex, ...
     * @return true 表示支持
     */
    default boolean supports(String name) {
        return getNames().contains(name);
    }

    Map<String, ExchangeApi> getAll();
}
