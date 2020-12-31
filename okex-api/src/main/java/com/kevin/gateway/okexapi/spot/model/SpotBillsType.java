package com.kevin.gateway.okexapi.spot.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SpotBillsType {

    DEPOSIT(1),//1.充值
    WITHDRAW(2),//2.提现
    BUY(7),//7.买入
    SELL(8),//8.卖出
    FROM_FUTURES(18),// 18.交割合约转入
    TO_FUTURES(19),//19.转出至交割合约
    TO_SUB_ACCOUNT(21),//20.转入子账户
    FROM_SUB_ACCOUNT(21),//21.转出至子账户
    TRANSACTION_FEE_REBATE(22),//22.返手续费
    OTC_PURCHASE(25),//25.OTC买入
    OTC_SELL(26),// 26.OTC卖出
    TO_FUNDING(29),//29.转出至资金账户
    FROM_FUNDING(30),//30.资金账户转入
    TO_C2C(31),//31.转出至法币
    FROM_C2C(32),//32.法币转入
    TO_MARGIN(33),//33.转出至杠杆
    FROM_MARGIN(34),//34.杠杆转入
    TOKENS_BORROWED(35),//35.借币
    TOKEN_REPAID(36),//36.还币
    MARKET_MAKER_REWARD(37),//37.市商计划送出
    TRANSFER_IN_FEE(39),//39.券商手续费转入
    TRANSFER_OUT_FEE(40),//40.券商手续费转出
    SPOT_FEE_PAID_WITH_LOYALTY_POINTS(41),//41.点卡抵扣币币手续费
    LOYALTY_POINT_PURCHASE(42),// 42.购买点卡
    LOYALTY_POINT_TRANSFER(43),// 43.点卡转让
    MARKET_MAKER_BONUS(44),//44.市商计划额外送出
    FROM_SPOT(46),//46.币币账户转入
    TO_SPOT(47),//47.转出 币币账户
    TO_ETT(48),//48.转出至组合
    FROM_ETT(49),//49.组合转入
    TO_MINING(50),//50.挖矿扣除
    FROM_MINING(51),//51.挖矿所得
    REFERRAL_PROGRAM(52),//52.收益倍增
    INCENTIVE_TOKENS(53),//53.鼓励金分配
    FROM_SAVINGS(55),//55.余币宝转入
    TO_SAVINGS(56),//56.转出至余币宝
    FROM_SWAP(57),//57.永续合约转入
    TO_SWAP(58),//58.转出至永续合约
    REPAY_CANDY(59),//59.还糖果
    HEDGE_FEE(60),//60.点卡抵扣杠杆手续费
    TO_HEDGE_ACCOUNT(61),//61.转出至套保账户
    FROM_HEDGE_ACCOUNT(62),//62.套保账户转入
    MARGIN_INTEREST_PAID_WITH_LOYALTY_POINTS(63),//63.点卡抵扣杠杆利息
    TO_MINING_STAKING_ACCOUNT(64),//64.矿池账户转入
    FROM_MINING_STAKING_ACCOUNT(65),//65.转出至矿池账户
    TO_OPTION_ACCOUNT(66),//66.转出至期权账户
    FROM_OPTION_ACCOUNT(67);//67.期权账户转入

    SpotBillsType(Integer value) {
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
    public static SpotBillsType valueOf(int value) {
        for (SpotBillsType spotBillsType : SpotBillsType.values()) {
            if (spotBillsType.value == value) {
                return spotBillsType;
            }
        }
        throw new IllegalArgumentException("无效现货账单流水类型");
    }
}
