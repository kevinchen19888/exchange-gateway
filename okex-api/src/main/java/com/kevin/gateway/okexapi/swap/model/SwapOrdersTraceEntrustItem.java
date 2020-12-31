package com.kevin.gateway.okexapi.swap.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 跟踪委托
 */
@Data
public class SwapOrdersTraceEntrustItem extends SwapOrdersBaseItem {


    /**
     * 回调幅度，填写值0.001（0.1%）\<=X\<=0.05（5%）
     */
    private BigDecimal callbackRate;

    /**
     * 激活价格 ，填写值0\<X\<=1000000
     */
    private BigDecimal triggerPrice;

    /**
     * 实际委托数量
     */
    private BigDecimal realAmount;
}

