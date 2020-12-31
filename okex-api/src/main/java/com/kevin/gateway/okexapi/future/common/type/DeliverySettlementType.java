package com.kevin.gateway.okexapi.future.common.type;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 交割：delivery 结算：
 */
public enum DeliverySettlementType {

    DELIVERY("delivery"),
    SETTLEMENT("settlement");

    private final String value;

    DeliverySettlementType(String desc) {
        this.value = desc;
    }

    @JsonCreator
    public static DeliverySettlementType fromValue(String value) {
        for (DeliverySettlementType type : DeliverySettlementType.values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("DeliverySettlementType 无效 ,value= " + value);
    }

    @JsonValue
    public String getValue() {
        return this.value;

    }

}
