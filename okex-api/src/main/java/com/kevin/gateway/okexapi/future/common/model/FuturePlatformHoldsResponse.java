package com.kevin.gateway.okexapi.future.common.model;

import com.kevin.gateway.okexapi.future.FutureMarketId;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class FuturePlatformHoldsResponse {

    /**
     * 合约ID，如BTC-USD-180213,BTC-USDT-191227
     */
    private FutureMarketId instrumentId;

    /**
     * 吃仓量
     */
    private BigDecimal amount;


    /**
     * 时间
     */
    private LocalDateTime timestamp;

}
