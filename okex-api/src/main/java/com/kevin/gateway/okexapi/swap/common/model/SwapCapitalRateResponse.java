package com.kevin.gateway.okexapi.swap.common.model;

import com.kevin.gateway.okexapi.swap.SwapMarketId;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
public class SwapCapitalRateResponse {

    /**
     * 合约ID
     */
    private SwapMarketId instrumentId;


    /**
     * 当期资金费率时间
     */
    private LocalDateTime fundingTime;


    /**
     * 当期资金费率
     */
    private BigDecimal fundingRate;


    /**
     * 结算时间
     */
    private LocalDateTime settlementTime;


    /**
     * 下一期预测资金费率
     */
    private BigDecimal estimatedRate;


}