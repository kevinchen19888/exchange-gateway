package com.kevin.gateway.okexapi.future.model;

import com.kevin.gateway.okexapi.future.FutureMarketId;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 获取合约挂单冻结数量
 */
@Data
public class HoldResponse {

    /**
     * 合约ID，如BTC-USD-180213,BTC-USDT-191227
     */
    private FutureMarketId instrumentId;

    /**
     * 冻结数量
     */
    private BigDecimal amount;

    /**
     * 查询时间
     */
    private LocalDateTime timestamp;
}

