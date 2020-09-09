package com.alchemy.gateway.broker.entity.type;

/**
 * 告警级别
 *
 * @author zoulingwei
 */
public enum AlertLevelEnum {
    WARN(0),

    ERROR(1),

    FATAL_ERROR(2);

    AlertLevelEnum(int value) {
        this.intValue = value;
    }

    private final int intValue;

    public int getIntValue() {
        return this.intValue;
    }

    public static AlertLevelEnum valueOf(int value) {
        for (AlertLevelEnum alertLevelEnum : AlertLevelEnum.values()) {
            if (alertLevelEnum.intValue == value) {
                return alertLevelEnum;
            }
        }
        throw new IllegalArgumentException("无效告警级别状态");
    }
}
