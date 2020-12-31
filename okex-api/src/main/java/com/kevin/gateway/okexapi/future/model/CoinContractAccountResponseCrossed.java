package com.kevin.gateway.okexapi.future.model;


import com.kevin.gateway.core.CoinPair;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CoinContractAccountResponseCrossed extends CoinContractAccountResponseBase {


    /**
     * 保证金（挂单冻结+持仓已用）
     */
    private BigDecimal margin;

    /**
     * 持仓已用保证金
     */
    private BigDecimal marginFrozen;

    /**
     * 挂单冻结保证金
     */
    private BigDecimal marginForUnfilled;

    /**
     * 已实现盈亏
     */
    private BigDecimal realizedPnl;

    /**
     * 未实现盈亏
     */
    private BigDecimal unrealizedPnl;

    /**
     * 保证金率
     */
    private BigDecimal marginRatio;

    /**
     * 维持保证金率
     */
    private BigDecimal maintMarginRatio;


    /**
     * 强平手续费
     */
    private BigDecimal liquiFeeRate;

    /**
     * 标的指数，如：BTC-USD，BTC-USDT
     */
    private CoinPair underlying;
}
