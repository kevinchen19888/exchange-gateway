package com.kevin.gateway.okexapi.swap.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 计划委托
 */
@Data
public class SwapOrdersPreEntrustItem extends SwapOrdersBaseItem {

    /**
     * 触发价格，填写值0\<X
     */
    private BigDecimal triggerPrice;

    /**
     * 委托价格，填写值0\<X\<=1000000
     */
    private BigDecimal algoPrice;

    /**
     * 实际成交数量
     */
    private BigDecimal realAmount;

    /**
     * 1:限价 2:市场价；
     */
    private BigDecimal algoType;

}

