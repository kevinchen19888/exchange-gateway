package com.alchemy.gateway.core.wallet;

/**
 * describe:
 *
 * @author zoulingwei
 */
public enum DepositWithdrawType {
    DEPOSIT(1),//充值

    WITHDRAW(2);//提币

    DepositWithdrawType(int value) {
        this.intValue = value;
    }

    private final int intValue;

    public int getIntValue() {
        return this.intValue;
    }

    public static DepositWithdrawType valueOf(int value) {
        for (DepositWithdrawType type : DepositWithdrawType.values()) {
            if (type.intValue == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("无效游标类型状态");
    }
}
