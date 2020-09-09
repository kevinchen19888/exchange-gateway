package com.alchemy.gateway.core.order;

/**
 * describe:止盈止损订单触发价运算符
 *
 * @author zoulingwei
 */
public enum OperatorType {

    GTE(0),//greater than and equal (>=)
    LTE(1);//less than and equal (<=)

    OperatorType(int value) {
        this.intValue = value;
    }

    private final int intValue;

    public int getIntValue() {
        return this.intValue;
    }

    public static OperatorType valueOf(int value) {
        for (OperatorType operatorType : OperatorType.values()) {
            if (operatorType.intValue == value) {
                return operatorType;
            }
        }
        throw new IllegalArgumentException("无效止盈止损订单触发价运算符状态");
    }
}
