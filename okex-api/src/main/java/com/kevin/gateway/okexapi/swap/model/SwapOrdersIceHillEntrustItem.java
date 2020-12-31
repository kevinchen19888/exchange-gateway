package com.kevin.gateway.okexapi.swap.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 冰山委托
 */

@Data
public class SwapOrdersIceHillEntrustItem extends SwapOrdersBaseItem {

    /**
     * 委托深度，填写值0\<=X\<=0.01（1%）
     */
    private BigDecimal algoVariance;

    /**
     * 单笔均值，填写2-500的整数（永续2-500的整数）
     */
    private int avgAmount;

    /**
     * 价格限制 ，填写值0\<X\<=1000000
     */
    private BigDecimal priceLimit;

    /**
     * 已成交量
     */
    private BigDecimal dealValue;
}

