package com.kevin.gateway.okexapi.future.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 1:币币账户
 * 3:交割合约
 * 4:法币账户
 * 5:币币杠杆账户
 * 6:资金账户
 * 8:余币宝账户
 * 9:永续合约账户
 * 12:期权合约
 * 14:挖矿账户
 * 17:借贷账户
 */
public enum AccoutFromToType {
    COIN_2_COIN(1, "币币账户"),
    FUTURE(3, "交割合约"),
    LEGAL_COIN(4, "法币账户"),
    LEVERAGE(5, "币币杠杆账户"),
    ASSET(6, "资金账户"),
    REST_COIN(8, "余币宝账户"),
    SWAP(9, "永续合约账户"),
    OPTION(12, "期权合约"),
    MINE(14, "挖矿账户"),
    BORROW(17, "借贷账户");

    private final Integer value;

    private final String name;

    AccoutFromToType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    @JsonCreator
    public static AccoutFromToType fromValue(String value) {
        for (AccoutFromToType type : AccoutFromToType.values()) {
            if (type.value.equals(Integer.valueOf(value))) {
                return type;
            }
        }
        throw new IllegalArgumentException("AccoutFromToType 无效 ,value= " + value);
    }

    @JsonValue
    public Integer getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }


}
