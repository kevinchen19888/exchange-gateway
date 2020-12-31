package com.kevin.gateway.okexapi.fundingaccount.domain;

import com.kevin.gateway.core.Coin;
import com.kevin.gateway.okexapi.base.util.AccountType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 账户资产估值Vo
 */
@Data
public class AssetValuationResponse {
    /**
     * 按照某一个法币为单位的估值，如BTC
     */
    private Coin valuationCurrency;
    /**
     * 预估资产
     */
    private BigDecimal balance;
    /**
     * 账户类型
     */
    private AccountType accountType;

    /**
     * 数据返回时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private LocalDateTime timestamp;
}
