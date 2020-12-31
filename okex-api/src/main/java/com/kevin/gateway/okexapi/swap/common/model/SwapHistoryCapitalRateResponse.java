package com.kevin.gateway.okexapi.swap.common.model;

import com.kevin.gateway.okexapi.swap.SwapMarketId;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
public class SwapHistoryCapitalRateResponse {

    /**
     * 合约ID
     */
    private SwapMarketId instrumentId;

    /**
     * 当期资金费率
     */
    private BigDecimal fundingRate;


    /**
     * 当期资金费率时间
     */
    private LocalDateTime fundingTime;


    /**
     * 实际资金费率
     */
    private BigDecimal realizedRate;


    /**
     * 利率
     */
    private BigDecimal interestRate;
}


