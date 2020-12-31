package com.kevin.gateway.okexapi.margin.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum MarginBillsType {
    TOKENS_BORROWED(3),//3.借币
    TOKERS_REPAID(4),// 4.还币
    INTEREST_ACCRUED(5),//5.计息
    BUY(7),//7.买入
    SELL(8),//8.卖出
    FROM_FUNDING(9),//9.资金账户转入
    FROM_C2C(10),//10.法币转入
    FORM_FUTURES(11),//11.交割合约转入
    FROM_SPOT(12),//12.币币转入
    FROM_ETT(13),//13.组合转入
    TO_FUNDING(14),//14.转出至资金账户
    TO_C2C(15),//15.转出至法币
    TO_SPOT(16),//16.转出至币币
    TO_FUTURES(17),//17.转出至交割合约
    TO_ETT(18),//18.转出至组合
    AUTO_INTEREST_PAYMENT(19),//19.强制还息
    FROM_SAVINGS(20),//20.余币宝转入
    TO_SAVINGS(21),//21.转出至余币宝
    FROM_SWAP(22),//22.永续合约转入
    TO_SWAP(23),//23.转出至永续合约
    LIQUIDATION_FEES(24),//24.强平费
    TO_HEDGE_ACCOUNT(25),//25.转出至套保账户
    FROM_HEDGE_ACCOUNT(26),//26.套保账户转入
    REPAY_CANDY(59),//59.还糖果
    HEDGE_ACCOUNT(60),//60.套保费
    TO_MARGIN(61),//61.币币杠杆账户转入
    FROM_MARGIN(62),//62.转出至币币杠杆账户
    TO_MINING_STAKING_ACCOUNT(64),//64.矿池账户转入
    FROM_MINING_STAKING_ACCOUNT(65),//65.转出至矿池账户
    TO_OPTION_ACCOUNT(66),//66.转出至期权账户
    FROM_OPTION_ACCOUNT(67);//67.期权账户转入

    MarginBillsType(Integer value) {
        this.value = value;
    }

    private final int value;

    public String getName() {
        return this.name().toLowerCase();
    }

    @JsonValue
    public int getIntValue() {
        return value;
    }

    @JsonCreator
    public static MarginBillsType valueOf(int value) {
        for (MarginBillsType spotBillsType : MarginBillsType.values()) {
            if (spotBillsType.value == value) {
                return spotBillsType;
            }
        }
        throw new IllegalArgumentException("无效币币杠杆账单流水类型");
    }
}
