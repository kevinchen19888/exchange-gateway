package com.kevin.gateway.okexapi.spot.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SpotBillsTransferFromOrTo {

    SPOT(1),//1:币币账户
    FUTURES(3),//3:交割合约
    C2C(4),//4:法币账户
    MARGIN(5),//5:币币杠杆账户
    FUNDING_ACCOUNT(6),//6:资金账户
    PIGGY_BANK(8),//8:余币宝账户
    SWAP(9),//9:永续合约账户
    OPTION(12),//12:期权合约
    MINING_ACCOUNT(14),//14:挖矿账户
    LOANS_ACCOUNT(17);//17:借贷账户

    SpotBillsTransferFromOrTo(Integer value) {
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
    public static SpotBillsTransferFromOrTo valueOf(int value) {
        for (SpotBillsTransferFromOrTo type : SpotBillsTransferFromOrTo.values()) {
            if (type.value == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("无效现货账单流水转入转出账户类型");
    }
}
