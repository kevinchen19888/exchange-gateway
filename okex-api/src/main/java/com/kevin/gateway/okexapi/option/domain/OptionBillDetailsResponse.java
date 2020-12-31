package com.kevin.gateway.okexapi.option.domain;

import com.kevin.gateway.core.Coin;
import com.kevin.gateway.okexapi.base.util.AccountType;
import com.kevin.gateway.okexapi.option.OptionMarketId;
import com.kevin.gateway.okexapi.option.util.TransactionType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OptionBillDetailsResponse {
    /**
     * 账单ID
     */
    private String ledgerId;
    /**
     * 账户币种, 如BTC
     */
    private Coin currency;
    /**
     * 变动金额
     */
    private BigDecimal amount;
    /**
     * 已实现收益，不含费用
     */
    private BigDecimal realizedPnl;
    /**
     * 流水来源类型（具体参见下表）
     */
    private TransactionType type;
    /**
     * 账单创建时间，ISO 8601格式
     */
    private LocalDateTime timestamp;
    /**
     * 如果类型是交易产生的，代表成交张数
     */
    private BigDecimal balance;
    /**
     * 如果type是trade或者fee，则会有该details字段将包含order，instrument信息,
     * 如果type是transfer，则会有该details字段将包含from，to信息
     */
    private String details;
    /**
     * 订单ID
     */
    private String orderId;
    /**
     * 合约ID
     */
    private OptionMarketId instrumentId;
    /**
     * 转出账户
     */
    private AccountType from;
    /**
     * 转入账户
     */
    private AccountType to;
}
