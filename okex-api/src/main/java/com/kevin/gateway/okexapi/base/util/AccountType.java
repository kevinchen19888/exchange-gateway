package com.kevin.gateway.okexapi.base.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum AccountType {
    /**
     * 总资产
     */
    TOTAL_ASSET(0),
    /**
     * 币币账户
     */
    SPOT(1),
    /**
     * 交割合约
     */
    FUTURES(3),
    /**
     * 法币账户
     */
    C2C(4),
    /**
     * 币币杠杆账户
     */
    MARGIN(5),
    /**
     * 资金账户
     */
    FUNDING_ACCOUNT(6),
    /**
     * 余币宝账户
     */
    PIGGYBANK(8),
    /**
     * 永续合约账户
     */
    SWAP(9),
    /**
     * 期权合约
     */
    OPTION(12),
    /**
     * 挖矿账户
     */
    MINING_ACCOUNT(14),
    /**
     * 交割usdt保证金账户
     */
    FUTURE_DEPOSIT_ACCOUNT(15),
    /**
     * 永续usdt保证金账户
     */
    SWAP_DEPOSIT_ACCOUNT(16),
    /**
     * 借贷账户
     */
    LOANS_ACCOUNT(17);

    private final int val;

    AccountType(int val) {
        this.val = val;
    }

    @JsonValue
    public int getVal() {
        return this.val;
    }

    @JsonCreator
    public static AccountType valueOf(int value) {
        for (AccountType accountType : AccountType.values()) {
            if (accountType.val == value) {
                return accountType;
            }
        }
        throw new IllegalArgumentException("无效账户类型");
    }

}
