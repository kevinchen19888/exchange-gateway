package com.alchemy.gateway.core.wallet;

/**
 * @author kevin chen
 */
public enum ChargeWithdrawTypeEnum {
    /**
     * 充币
     */
    CHARGE(0),
    /**
     * 提币
     */
    WITHDRAW(1);

    private final int value;

    ChargeWithdrawTypeEnum(int value) {
        this.value = value;
    }

    public int getIntValue() {
        return this.value;
    }
}
