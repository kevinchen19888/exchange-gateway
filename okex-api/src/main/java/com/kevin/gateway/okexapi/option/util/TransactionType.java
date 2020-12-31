package com.kevin.gateway.okexapi.option.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 流水来源类型
 */
public enum TransactionType {
    /**
     * 转入/转出
     */
    TRANSFER("transfer"),
    /**
     * 交易
     */
    MATCH("match"),
    /**
     * 手续费
     */
    FEE("fee"),
    /**
     * 交割/结算
     */
    SETTLEMENT("settlement"),
    /**
     * 强平/减仓
     */
    LIQUIDATION("liquidation");

    private final String val;

    TransactionType(String transfer) {
        this.val = transfer;
    }

    @JsonValue
    public String getVal() {
        return val;
    }

    @JsonCreator
    public static TransactionType fromVal(String val) {
        for (TransactionType transactionType : TransactionType.values()) {
            if (transactionType.val.equals(val)) {
                return transactionType;
            }
        }
        throw new IllegalArgumentException("无效流水来源类型,s:" + val);
    }
}
