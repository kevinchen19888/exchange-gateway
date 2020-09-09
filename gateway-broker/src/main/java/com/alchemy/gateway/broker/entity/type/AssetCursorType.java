package com.alchemy.gateway.broker.entity.type;

/**
 * describe:游标类型
 *
 * @author zoulingwei
 */
public enum AssetCursorType {
    DEPOSIT_WITHDRAW(0),

    ASSET_TRANSFER(1),

    HISTORY_ORDER(2);

    AssetCursorType(int value) {
        this.intValue = value;
    }

    private final int intValue;

    public int getIntValue() {
        return this.intValue;
    }

    public static AssetCursorType valueOf(int value) {
        for (AssetCursorType assetCursorType : AssetCursorType.values()) {
            if (assetCursorType.intValue == value) {
                return assetCursorType;
            }
        }
        throw new IllegalArgumentException("无效游标类型状态");
    }
}
