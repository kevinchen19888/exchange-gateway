package com.alchemy.gateway.broker.entity.type;

/**
 * 告警级别
 *
 * @author zoulingwei
 */
public enum AlertErrorTypeEnum {
    CHARGE_WITHDRAW(0),

    ORDER(1),

    ACCOUNT(2);

    AlertErrorTypeEnum(int value) {
        this.intValue = value;
    }

    private final int intValue;

    public int getIntValue() {
        return this.intValue;
    }

    public static AlertErrorTypeEnum valueOf(int value) {
        for (AlertErrorTypeEnum alertErrorTypeEnum : AlertErrorTypeEnum.values()) {
            if (alertErrorTypeEnum.intValue == value) {
                return alertErrorTypeEnum;
            }
        }
        throw new IllegalArgumentException("无效告警级别状态");
    }
}
