package com.kevin.gateway.okexapi.swap.model;

import lombok.Data;

import java.math.BigDecimal;


/**
 * 时间加权
 */
@Data
public class SwapOrdersTimeWeightItem extends SwapOrdersBaseItem {

    /**
     * 扫单范围，填写值0.005（0.5%）\<=X\<=0.01（1%
     */
    private BigDecimal sweepRange;

    /**
     * 扫单比例，填写值 0.01\<=X\<=1
     */
    private BigDecimal sweepRatio;

    /**
     * 单笔上限，填写值10\<=X\<=2000（永续2-500的整数）
     */
    private BigDecimal singleLimit;

    /**
     * 价格限制，填写值0\<X\<=1000000
     */
    private BigDecimal priceLimit;

    /**
     * 委托间隔，填写值5\<=X\<=120
     */
    private int timeInterval;

    /**
     * 已委托量
     */
    private BigDecimal dealValue;

}

