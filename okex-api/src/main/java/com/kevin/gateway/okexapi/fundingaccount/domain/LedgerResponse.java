package com.kevin.gateway.okexapi.fundingaccount.domain;

import com.kevin.gateway.core.Coin;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 账单流水Vo
 */
@Data
public class LedgerResponse {

    /**
     * 账单ID
     */
    private String ledgerId;
    /**
     * 币种
     */
    private Coin currency;
    /**
     * 余额
     */
    private BigDecimal balance;
    /**
     * 变动数量
     */
    private BigDecimal amount;
    /**
     * 账单类型,eg:Get from activity
     */
    private String typename;
    /**
     * 手续费
     */
    private BigDecimal fee;
    /**
     * 账单创建时间
     */
    private LocalDateTime timestamp;
}
