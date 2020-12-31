package com.kevin.gateway.okexapi.future.model;


import com.kevin.gateway.okexapi.future.FutureMarketId;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CoinContractAccountContractResponse {

    /**
     * 逐仓可用余额
     */
    private BigDecimal availableQty;

    /**
     * 逐仓账户余额
     */
    private BigDecimal fixedBalance;

    /**
     * 合约ID，如BTC-USD-180213，BTC-USDT-191227
     */
    private FutureMarketId instrumentId;


    /**
     * 挂单冻结保证金
     */
    private BigDecimal marginForUnfilled;

    /**
     * 持仓已用保证金
     */
    private BigDecimal marginFrozen;


    /**
     * 已实现盈亏
     */
    private BigDecimal realizedPnl;

    /**
     * 未实现盈亏
     */
    private BigDecimal unrealizedPnl;


}
