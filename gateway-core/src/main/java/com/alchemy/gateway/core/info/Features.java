package com.alchemy.gateway.core.info;

import com.alchemy.gateway.core.common.MarketType;

import java.util.List;

/**
 * 交易所特性
 */
public interface Features {

    /**
     * 交易所支持的市场类型
     *
     * @return 交易所支持的市场类型列表
     */
    List<MarketType> supportMarketTypes();


    /**
     * 判断是否支持指定的市场类型
     *
     * @param marketType 市场类型
     * @return true 支持
     */
    default boolean supportsMarketType(MarketType marketType) {
        return supportMarketTypes().contains(marketType);
    }
}
