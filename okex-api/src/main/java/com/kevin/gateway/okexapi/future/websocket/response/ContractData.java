package com.kevin.gateway.okexapi.future.websocket.response;

import com.kevin.gateway.okexapi.future.FutureMarketId;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 合约信息
 */
@Data
public class ContractData  {

    /**
     * 逐仓可用余额
     */
    private BigDecimal availableQty;
    /**
     * 	逐仓账户余额
     */
    private BigDecimal fixedBalance;
    /**
     * 合约ID，如BTC-USD-191227,BTC-USDT-191227
     */
    private FutureMarketId instrumentId;
    /**
     * 多仓最大可开张数
     */
    private int longOpenMax;
    /**
     * 挂单冻结保证金
     */
    private BigDecimal marginForUnfilled;
    /**
     * 冻结的保证金(成交以后仓位所需的)
     */
    private BigDecimal marginFrozen;
    /**
     * 已实现盈亏
     */
    private BigDecimal realizedPnl;
    /**
     * 空仓最大可开张数
     */
    private int shortOpenMax;
    /**
     * 数据更新时间
     */
    private LocalDateTime timestamp;
    /**
     * 未实现盈亏
     */
    private BigDecimal unrealizedPnl;

}