package com.kevin.gateway.okexapi.swap.model;

import com.kevin.gateway.core.Coin;
import com.kevin.gateway.okexapi.future.type.AccoutFlowSourceType;
import com.kevin.gateway.okexapi.swap.SwapMarketId;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class SwapAccountFlowResponse {

    /**
     * 账单id
     */
    private String ledgerId;

    /**
     * 变动金额
     */
    private BigDecimal amount;

    /**
     * 账单类型
     */
    private AccoutFlowSourceType type;

    /**
     * 手续费
     */
    private BigDecimal fee;

    /**
     * 账单创建时间
     */
    private LocalDateTime timestamp;

    /**
     * 合约ID
     */
    private SwapMarketId instrumentId;

    /**
     * 币种，如：btc
     */
    private Coin currency;

    /**
     * 详细
     */
    private SwapAccountFlowDetail details;


    /**
     * 开仓平仓的张数（开仓为正数，平仓为负数，当type是fee时，balance为0）
     */
    private BigDecimal balance;


}

