package com.kevin.gateway.okexapi.swap.common.model;

import com.kevin.gateway.okexapi.swap.SwapMarketId;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class SwapPlatformHoldsResponse {

    /**
     * 合约ID
     */
    private SwapMarketId instrumentId;

    /**
     * 吃仓量
     */
    private BigDecimal amount;


    /**
     * 时间
     */
    private LocalDateTime timestamp;

}
