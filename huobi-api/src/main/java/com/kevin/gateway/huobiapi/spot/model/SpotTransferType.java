package com.kevin.gateway.huobiapi.spot.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SpotTransferType {
    //otc-to-pro（otc到现货）, pro-to-otc（现货到otc）, futures-to-pro（交割合约到现货）, pro-to-futures（现货到交割合约）,
    // dm-swap-to-pro（永续合约到现货）, dm-pro-to-swap（现货到永续合约）, margin-transfer-in（转入到逐仓杠杆）,
    // margin-transfer-out（从逐仓杠杆转出）, pro-to-super-margin（现货到全仓杠杆）, super-margin-to-pro（全仓杠杆到现货）,
    // master-transfer-in（转入到母用户）, master-transfer-out（从母用户转出）, sub-transfer-in（转入到子用户）,
    // sub-transfer-out（从子用户转出）
    OTC_TO_PRO("otc-to-pro"),
    pro_to_otc("pro-to-otc"),
    FUTURES_TO_PRO("futures-to-pro"),
    PRO_TO_FUTURES("pro-to-futures"),
    DM_SWAP_TO_PRO("dm-swap-to-pro"),
    DM_PRO_TO_SWAP("dm-pro-to-swap"),
    MARGIN_TRANSFER_IN("margin-transfer-in"),
    MARGIN_TRANSFER_OUT("margin-transfer-out"),
    PRO_TO_SUPER_MARGIN("pro-to-super-margin"),
    SUPER_MARGIN_TO_PRO("super-margin-to-pro"),
    MASTER_TRANSFER_IN("master-transfer-in"),
    MASTER_TRANSFER_OUT("master-transfer-out"),
    SUB_TRANSFER_IN("sub-transfer-in"),
    SUB_TRANSFER_OUT("sub-transfer-out"),

    MASTER_POINT_TRANSFER_IN("master-point-transfer-in"), //（子用户划转给母用户点卡）
    MASTER_POINT_TRANSFER_OUT("master-point-transfer-out");//（母用户划转给子用户点卡）

    SpotTransferType(String type) {
        this.type = type;
    }

    private final String type;

    @JsonValue
    public String getType() {
        return type;
    }

    @JsonCreator
    public static SpotTransferType fromOf(String type) {
        for (SpotTransferType result : SpotTransferType.values()) {
            if (result.type.equals(type)) {
                return result;
            }
        }
        throw new IllegalArgumentException("无效火币财务流水划转类型");
    }
}
