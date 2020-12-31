package com.kevin.gateway.okexapi.swap.common.model;

import com.kevin.gateway.okexapi.swap.SwapMarketId;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class SwapPriceLimitResponse {

    /**
     * 合约ID
     */
    private SwapMarketId instrumentId;

    /**
     * 最高价
     */
    private BigDecimal highest;


    /**
     * 最低价
     */
    private BigDecimal lowest;

    /**
     * 时间
     */
    private LocalDateTime timestamp;


}
