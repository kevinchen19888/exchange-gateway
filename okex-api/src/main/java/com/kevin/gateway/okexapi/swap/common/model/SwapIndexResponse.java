package com.kevin.gateway.okexapi.swap.common.model;


import com.kevin.gateway.okexapi.swap.SwapMarketId;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class SwapIndexResponse {

    /**
     * 合约ID
     */
    private SwapMarketId instrumentId;

    /**
     * 价格指数
     */
    private BigDecimal index;


    /**
     * 时间
     */
    private LocalDateTime timestamp;


}
