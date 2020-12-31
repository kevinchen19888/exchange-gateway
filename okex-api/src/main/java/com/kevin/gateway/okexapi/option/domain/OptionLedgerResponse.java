package com.kevin.gateway.okexapi.option.domain;

import com.kevin.gateway.core.Coin;
import com.kevin.gateway.okexapi.base.util.AccountType;
import com.kevin.gateway.okexapi.option.OptionMarketId;
import com.kevin.gateway.okexapi.option.util.TransactionType;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 获取账单流水 vo
 */
@Data
public class OptionLedgerResponse {
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


    public void setOther(String key, Object val) {
        this.other.put(key, val);
    }

    @JsonAnySetter
    private Map<String, Object> other = new HashMap<>();

}
