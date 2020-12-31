package com.kevin.gateway.okexapi.option.websocket.response;

import com.kevin.gateway.core.Coin;
import com.kevin.gateway.core.CoinPair;
import com.kevin.gateway.okexapi.option.websocket.response.type.OptionAccountStatus;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 账户信息
 */
@Data
public class AccountData {
    /**
     * 账户币种, 如BTC
     */
    private Coin currency;
    /**
     * 标的指数，如BTC-USD
     */
    private CoinPair underlying;
    /**
     * 权益
     */
    private BigDecimal equity;
    /**
     * 总余额
     */
    private BigDecimal totalAvailBalance;
    /**
     * 保证金余额
     */
    private BigDecimal marginBalance;
    /**
     * 总挂单保证金
     */
    private BigDecimal marginForUnfilled;
    /**
     * 已用保证金（IMR，包含挂单和持仓保证金）
     */
    private BigDecimal marginFrozen;
    /**
     * 持仓最低维持保证金
     */
    private BigDecimal maintenanceMargin;
    /**
     * 当期已实现盈亏汇总
     */
    private BigDecimal realizedPnl;
    /**
     * 当期未实现盈亏汇总
     */
    private BigDecimal unrealizedPnl;
    /**
     * 期权总市值
     */
    private BigDecimal optionValue;
    /**
     * 账户总delta
     */
    private BigDecimal delta;
    /**
     * 	账户总vega
     */
    private BigDecimal vega;
    /**
     * 账户总gamma
     */
    private BigDecimal gamma;
    /**
     * 账户总theta
     */
    private BigDecimal theta;
    /**
     * 	风险乘数 （目前该字段暂未启用）
     */
    private String riskFactor;
    /**
     * 规模乘数 （目前该字段暂未启用）
     */
    private String marginMultiplier;
    /**
     * "0": 正常，"1": 延迟减仓中，"2": 减仓中
     */
    private OptionAccountStatus accountStatus;

    private String availMargin;

    private String maxWithdraw;

}
