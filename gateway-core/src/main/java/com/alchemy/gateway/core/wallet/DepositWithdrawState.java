package com.alchemy.gateway.core.wallet;

/**
 * describe:充提记录状态
 *
 * @author zoulingwei
 */
public enum DepositWithdrawState {
    BEING(1),//处理中

    FINISH(2),//完成

    FAILED(3);//失败

    DepositWithdrawState(int value) {
        this.intValue = value;
    }

    private final int intValue;

    public int getIntValue() {
        return this.intValue;
    }

    public static DepositWithdrawState valueOf(int value) {
        for (DepositWithdrawState state : DepositWithdrawState.values()) {
            if (state.intValue == value) {
                return state;
            }
        }
        throw new IllegalArgumentException("无效充提记录状态状态");
    }
}
