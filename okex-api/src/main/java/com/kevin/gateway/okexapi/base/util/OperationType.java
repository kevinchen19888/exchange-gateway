package com.kevin.gateway.okexapi.base.util;

/**
 * 资金账户操作类型
 */
public enum OperationType {
    /**
     * 充值
     */
    DEPOSIT(1),
    /**
     * 提现
     */
    WITHDRAWAL(2),
    /**
     * 撤销提现
     */
    CANCEL_WITHDRAWAL(13),
    /**
     * 转入交割合约账户
     */
    INTO_FUTURES_ACCOUNT(18),
    /**
     * 交割合约账户转出
     */
    OUT_OF_FUTURES_ACCOUNT(19),
    /**
     * 转入子账户
     */
    INTO_SUB_ACCOUNT(20),
    /**
     * 子账户转出
     */
    OUT_OF_SUB_ACCOUNT(21),
    /**
     * 领取
     */
    CLAIM(28),
    /**
     * 转入指数交易区
     */
    INTO_ETT_ACCOUNT(29),
    /**
     * 指数交易区转出
     */
    OUT_OF_ETT_ACCOUNT(30),
    /**
     * 转入法币账户
     */
    INTO_C_2_CACCOUNT(31),
    /**
     * 法币账户转出
     */
    OUT_OF_C_2_CACCOUNT(32),
    /**
     * 转入币币杠杆账户
     */
    INTO_MARGIN_ACCOUNT(33),
    /**
     * 币币杠杆账户转出
     */
    OUT_OF_MARGIN_ACCOUNT(34),
    /**
     * 转入币币账户
     */
    INTO_SPOT_ACCOUNT(37),
    /**
     * 币币账户转出
     */
    OUT_OF_SPOT_ACCOUNT(38),
    /**
     * 点卡抵扣手续费
     */
    SERVICE_FEES_SETTLEMENT(41),
    /**
     * 购买点卡
     */
    LOYALTY_POINTS_PURCHASE(42),
    /**
     * 点卡转让
     */
    LOYALTY_POINTS_TRANSFER(43),
    /**
     * 撤销点卡转让
     */
    CANCEL_TRANSFER_OF_LOYALTY_POINTS(44),
    /**
     * 系统冲正
     */
    SYSTEM_REVERSE(47),
    /**
     * 活动得到
     */
    GET_FROM_ACTIVITY(48),
    /**
     * 活动送出
     */
    SEND_BY_ACTIVITY(49),
    /**
     * 预约得到
     */
    SUBSCRIPTION_ALLOTMENT(50),
    /**
     * 预约扣除
     */
    SUBSCRIPTION_COST(51),
    /**
     * 发红包;
     */
    SEND_BY_RED_PACKET(52),
    /**
     * 抢红包
     */
    RECEIVE_FROM_RED_PACKET(53),
    /**
     * 红包退还
     */
    BACK_FROM_RED_PACKET(54),
    /**
     * 转入永续合约账户
     */
    TO_SWAP_ACCOUNT(55),
    /**
     * 永续合约账户转出
     */
    FROM_SWAP_ACCOUNT(56),
    /**
     * 转入余币宝
     */
    TO_SAVINGS_ACCOUNT(57),
    /**
     * 余币宝转出
     */
    FROM_SAVINGS_ACCOUNT(58),
    /**
     * 套保账户转出;
     */
    FROM_HEDGING_ACCOUNT(59),
    /**
     * 转入套保账户
     */
    TO_HEDGING_ACCOUNT(60),
    /**
     * 兑换
     */
    EXCHANGE(61),
    /**
     * 期权账户转出;
     */
    FROM_OPTIONS_ACCOUNT(62),
    /**
     * 转入期权账户
     */
    TO_OPTIONS_ACCOUNT(63),
    /**
     * 挖矿账户转出;
     */
    FROM_MINING_STAKING_ACCOUNT(66),
    /**
     * 转入挖矿账户
     */
    TO_MINING_STAKING_ACCOUNT(67),
    /**
     *
     */
    PASS_BENEFIT_REDEMPTION(68),
    /**
     *
     */
    PASS_BENEFIT_DELIVERY(69),
    /**
     * 借贷账户转出;
     */
    FROM_LOANS_ACCOUNT(70),
    /**
     * 转入借贷账户
     */
    TO_LOANS_ACCOUNT(71);

    private final int val;

    OperationType(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }
}
