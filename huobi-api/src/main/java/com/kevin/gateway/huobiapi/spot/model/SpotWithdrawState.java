package com.kevin.gateway.huobiapi.spot.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SpotWithdrawState {
    VERIFYING("verifying"),	//待验证
    FAILED("failed"),//	验证失败
    SUBMITTED("submitted"),	//已提交
    REEXAMINE("reexamine"),//	审核中
    CANCELED("canceled"),	//已撤销
    PASS("pass"),	//审批通过
    REJECT("reject"),//	审批拒绝
    PRE_TRANSFER("pre-transfer"),	//处理中
    WALLET_TRANSFER("wallet-transfer"),	//已汇出
    WALLET_REJECT("wallet-reject"),//	钱包拒绝
    CONFIRMED("confirmed"),//	区块已确认
    CONFIRM_ERROR("confirm-error"),	//区块确认错误
    REPEALED("repealed");//	已撤销

    SpotWithdrawState(String state) {
        this.state = state;
    }

    private final String state;

    @JsonValue
    public String getState() {
        return state;
    }

    @JsonCreator
    public static SpotWithdrawState fromOf(String state) {
        for (SpotWithdrawState type : SpotWithdrawState.values()) {
            if (type.state.equals(state)) {
                return type;
            }
        }
        throw new IllegalArgumentException("无效火币提币状态");
    }
}
