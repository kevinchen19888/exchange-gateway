package com.kevin.gateway.okexapi.swap.model;

import com.kevin.gateway.core.Coin;
import com.kevin.gateway.core.CoinPair;
import com.kevin.gateway.okexapi.future.type.MarginMode;
import com.kevin.gateway.okexapi.swap.SwapMarketId;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CoinSwapAccountResponse {

    /**
     * 账户余额币种
     */
    private Coin currency;

    /**
     * 标的指数，如：BTC-USD-SWAP，BTC-USDT-SWAP
     */
    private CoinPair underlying;

    /**
     * 账户权益
     */
    private BigDecimal equity;

    /**
     * 账户余额
     */
    private BigDecimal totalAvailBalance;

    /**
     * 逐仓账户余额
     */
    private BigDecimal fixedBalance;

    /**
     * 已用保证金
     */
    private BigDecimal margin;

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
     * 开仓冻结保证金
     */
    private BigDecimal marginFrozen;

    /**
     * 合约名称
     */
    private SwapMarketId instrumentId;

    /**
     * 创建时间
     */
    private LocalDateTime timestamp;

    /**
     * 仓位模式
     * 全仓：crossed
     * 逐仓：fixed
     */
    private MarginMode marginMode;

    /**
     * 维持保证金率
     */
    private BigDecimal maintMarginRatio;

    /**
     * 可划转数量
     */
    private BigDecimal maxWithdraw;

}

