package com.alchemy.gateway.broker.entity.type;

/**
 * 用户状态
 *
 * @author zoulingwei
 */
public enum AccountStatusEnum {

    /**
     * 启用
     */
    ENABLED(0),
    /**
     * 禁用
     */
    DISABLED(1),
    /**
     * 资产异常
     */
    ASSET_ABNORMAL(2),
    /**
     * 删除
     */
    DELETED(3);

    AccountStatusEnum(int value) {
        this.intValue = value;
    }

    private final int intValue;

    public int getIntValue() {
        return this.intValue;
    }

    public static AccountStatusEnum valueOf(int value) {
        for (AccountStatusEnum accountStatusEnum : AccountStatusEnum.values()) {
            if (accountStatusEnum.intValue == value) {
                return accountStatusEnum;
            }
        }
        throw new IllegalArgumentException("无效账户状态");
    }

}
