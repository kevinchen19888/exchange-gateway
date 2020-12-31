package com.kevin.gateway.okexapi.swap.model;

import com.kevin.gateway.okexapi.swap.SwapMarketId;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * 获取合约挂单冻结数量
 */
@Data
public class SwapHoldResponse {

    /**
     * 合约ID，如BTC-USD-SWAP
     */
    private SwapMarketId instrumentId;

    /**
     * 冻结数量
     */
    private BigDecimal amount;

    /**
     * 查询时间
     */
    private LocalDateTime timestamp;
}
