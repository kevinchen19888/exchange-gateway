package com.alchemy.gateway.core.common;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 交易所市场OrderLimit基类,具体规则由各交易所api实现
 *
 * @author kevin chen
 */
@Data
public abstract class OrderLimit {
    private String symbol;
    /**
     * 最小下单数量
     */
    private BigDecimal minOrderAmount;

}
