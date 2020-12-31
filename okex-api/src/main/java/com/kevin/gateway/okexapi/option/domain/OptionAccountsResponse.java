package com.kevin.gateway.okexapi.option.domain;

import com.kevin.gateway.core.Coin;
import com.kevin.gateway.core.CoinPair;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 获取单个标的物账户信息 vo
 */
@Data
@ToString(callSuper = true)
public class OptionAccountsResponse extends OptionErrorResponse {
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
     * 已用保证金（IMR，包含挂单和持仓保证金）
     */
    private BigDecimal marginFrozen;
    /**
     * 可用保证金
     */
    private BigDecimal availMargin;
    /**
     * 总挂单保证金
     */
    private BigDecimal marginForUnfilled;
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
     * 账户总vega
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
     * 风险乘数 （目前该字段暂未启用）
     */
    private String riskFactor;
    /**
     * 规模乘数 （目前该字段暂未启用）
     */
    private String marginMultiplier;
    /**
     * "0": 正常，
     * "1": 延迟减仓中，
     * "2": 减仓中
     */
    private AccountStatus accountStatus;

    @JsonAnySetter
    private Map<String, String> others = new HashMap<>();

    public void setOthers(String key, String val) {
        this.others.put(key, val);
    }

    public enum AccountStatus {
        /**
         * "0": 正常，
         */
        NORMAL(0),
        /**
         * "1": 延迟减仓中，
         */
        DELAYED_DELEVERAGING(1),
        /**
         * "2": 减仓中
         */
        DELEVERAGING(2);

        private final int val;

        AccountStatus(int val) {
            this.val = val;
        }

        @JsonValue
        public int getVal() {
            return val;
        }

        @JsonCreator
        public static AccountStatus fromVal(int val) {
            for (AccountStatus accountStatus : AccountStatus.values()) {
                if (accountStatus.val == val) {
                    return accountStatus;
                }
            }
            throw new IllegalArgumentException("无效订单交易方向状态,s:" + val);
        }
    }

}
