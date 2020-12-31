package com.kevin.gateway.okexapi.future.model;

import com.kevin.gateway.okexapi.future.type.MarginMode;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 单个合约持仓信息  逐仓参数
 */
@Data
public class SingleContractPositionResponseFixed extends SingleContractPositionResponseBase {

    private MarginMode marginMode = MarginMode.fixed;


    /**
     * 多仓杠杆倍数
     */
    private int longLeverage;

    /**
     * 空仓强平价格
     */
    private BigDecimal shortLiquiPrice;


    /**
     * 空仓杠杆倍数
     */
    private int shortLeverage;


    /**
     * 空仓保证金率
     */
    private BigDecimal shortMarginRatio;


    /**
     * 空仓维持保证金率
     */
    private BigDecimal shortMaintMarginRatio;


    /**
     * 多仓保证金率
     */
    private BigDecimal longMarginRatio;

    /**
     * 多仓维持保证金率
     */
    private BigDecimal longMaintMarginRatio;


    /**
     * 多仓强平价格
     */
    private BigDecimal longLiquiPrice;

}
