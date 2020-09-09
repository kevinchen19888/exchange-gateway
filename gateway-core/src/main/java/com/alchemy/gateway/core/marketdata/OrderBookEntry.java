package com.alchemy.gateway.core.marketdata;

import lombok.Data;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 深度数据项
 */
@Data
public class OrderBookEntry {
    /**
     * 深度价格
     */
    BigDecimal price;
    /**
     * 价格数量
     */
    BigDecimal size;

    /**
     * 备用字段，各交易所特色字段存放；key为字段名，value为值
     */
    Map<String, String> reserveMap = new HashMap<>();
}
