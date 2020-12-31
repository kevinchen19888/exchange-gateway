package com.kevin.gateway.okexapi.swap.common.model;

import com.kevin.gateway.okexapi.swap.SwapMarketId;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class SwapMarkPriceResponse {

    /**
     * 合约ID
     */
    private SwapMarketId instrumentId;

    /**
     * 指定合约品种的标记价格
     */
    private BigDecimal markPrice;


    /**
     * 时间
     */
    private LocalDateTime timestamp;


}
