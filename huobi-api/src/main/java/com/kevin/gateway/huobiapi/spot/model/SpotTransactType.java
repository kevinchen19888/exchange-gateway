package com.kevin.gateway.huobiapi.spot.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 账户流水变动类型
 */
public enum SpotTransactType {
    //trade (交易),etf（ETF申购）, transact-fee（交易手续费）, fee-deduction（手续费抵扣）, transfer（划转）, credit（借币）,
    // liquidation（清仓）, interest（币息）, deposit（充币），withdraw（提币）, withdraw-fee（提币手续费）, exchange（兑换）,
    // other-types（其他）,rebate（交易返佣）

    TRADE("trade"),
    ETF("etf"),
    TRANSACT_FEE("transact-fee"),
    FEE_DEDUCTION("fee-deduction"),
    TRANSFER("transfer"),
    CREDIT("credit"),
    LIQUIDATION("liquidation"),
    INTEREST("interest"),
    DEPOSIT("deposit"),
    WITHDRAW("withdraw"),
    WITHDRAW_FEE("withdraw-fee"),
    EXCHANGE("exchange"),
    OTHER_TYPES("other-types"),
    REBATE("rebate");

    SpotTransactType(String type) {
        this.type = type;
    }

    private final String type;

    @JsonValue
    public String getType() {
        return type;
    }

    @JsonCreator
    public static SpotTransactType fromOf(String type) {
        for (SpotTransactType result : SpotTransactType.values()) {
            if (result.type.equals(type)) {
                return result;
            }
        }
        throw new IllegalArgumentException("无效火币账户流水变动类型");
    }
}
