package com.alchemy.gateway.core.order;

/**
 * describe:成交角色
 *
 * @author zoulingwei
 */
public enum RoleType {
    maker(0),

    taker(1);

    RoleType(int value) {
        this.intValue = value;
    }

    private final int intValue;

    public int getIntValue() {
        return this.intValue;
    }

    public static RoleType valueOf(int value) {
        for (RoleType roleType : RoleType.values()) {
            if (roleType.intValue == value) {
                return roleType;
            }
        }
        throw new IllegalArgumentException("无效成交角色状态");
    }
}
