package com.kevin.gateway.okexapi.swap.websocket.response;

import com.kevin.gateway.core.Coin;
import com.kevin.gateway.core.CoinPair;
import com.kevin.gateway.okexapi.future.type.MarginMode;
import com.kevin.gateway.okexapi.swap.SwapMarketId;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
/**
 * 账户信息
 */
@Data
public class AccountData {
    /**
     * 账户权益
     */
    private BigDecimal equity;
    /**
     * 逐仓账户余额
     */
    private BigDecimal fixedBalance;
    /**
     * 合约名称
     */
    private SwapMarketId instrumentId;
    /**
     * 维持保证金率
     */
    private BigDecimal maintMarginRatio;
    /**
     * 已用保证金
     */
    private BigDecimal margin;
    /**
     * 开仓冻结保证金
     */
    private BigDecimal marginFrozen;
    /**
     * 账户类型
     * 逐仓:fixed
     * 全仓:crossed
     */
    private MarginMode marginMode;
    /**
     * 保证金率
     */
    private BigDecimal marginRatio;
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
     * 未实现盈亏
     */
    private BigDecimal unrealizedPnl;
    /**
     * 可用保证金
     */
    private BigDecimal availableQty;
    /**
     * 多仓最大可开张数
     */
    private int longOpenMax;
    /**
     * 空仓最大可开张数
     */
    private int shortOpenMax;

    private Coin currency;

    private CoinPair underlying;

}
