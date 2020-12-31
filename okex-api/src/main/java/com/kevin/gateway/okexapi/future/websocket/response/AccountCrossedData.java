package com.kevin.gateway.okexapi.future.websocket.response;

import com.kevin.gateway.core.Coin;
import com.kevin.gateway.core.CoinPair;
import com.kevin.gateway.okexapi.future.type.MarginMode;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 *账户全仓信息
 */
@Data
public class AccountCrossedData implements AccountData {
    /**
     * 可用保证金
     */
    private BigDecimal available;
    /**
     * 可划转数量
     */
    private BigDecimal canWithdraw;
    /**
     * 账户余额币种，如：BTC,USDT
     */
    private Coin currency;
    /**
     * 账户权益
     */
    private BigDecimal equity;
    /**
     * 强平模式：tier（梯度强平）
     */
    private String liquiMode;
    /**
     * 维持保证金率
     */
    private BigDecimal maintMarginRatio;
    /**
     * 已用保证金
     */
    private BigDecimal margin;
    /**
     * 挂单冻结保证金
     */
    private BigDecimal marginForUnfilled;
    /**
     * 持仓已用保证金
     */
    private BigDecimal marginFrozen;
    /**
     * 账户类型
     * 全仓：crossed
     */
    private MarginMode marginMode = MarginMode.crossed;
    /**
     * 保证金率
     */
    private BigDecimal marginRatio;
    /**
     * 最大可开张数
     */
    private int openMax;
    /**
     * 已实现盈亏
     */
    private BigDecimal realizedPnl;
    /**
     * 账户数据更新时间
     */
    private LocalDateTime timestamp;
    /**
     * 账户余额
     */
    private BigDecimal totalAvailBalance;
    /**
     * 标的指数，如：BTC-USD,BTC-USDT
     */
    private CoinPair underlying;
    /**
     * 未实现盈亏
     */
    private BigDecimal unrealizedPnl;

}
