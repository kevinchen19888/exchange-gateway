package com.kevin.gateway.okexapi.future.common.model;


import com.kevin.gateway.okexapi.future.FutureMarketId;
import com.kevin.gateway.okexapi.future.common.type.DeliverySettlementType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class SettlementHistoryResponse {

    /**
     * 合约ID，如BTC-USD-180213,BTC-USDT-191227
     */
    private FutureMarketId instrumentId;


    /**
     * 交割：delivery 结算：settlement
     */
    private DeliverySettlementType type;

    /**
     * 交割/结算价格
     */
    private BigDecimal settlementPrice;

    /**
     * 穿仓用户亏损分摊
     */
    private BigDecimal clawbackLoss;


    /**
     * 准备金分摊
     */
    private BigDecimal reserve;

    /**
     * 分摊比例 如 0 ，0.01（1%）
     */
    private BigDecimal clawbackRate;

    /**
     * 交割/结算日期
     */
    private LocalDateTime timestamp;


}
