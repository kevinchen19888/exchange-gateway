package com.kevin.gateway.okexapi.future.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 流水来源类型	参数类型	描述
 * transfer	String	资金转入/转出
 * match	String	开多/开空/平多/平空
 * fee	String	手续费
 * settlement	String	清算/分摊/多结算/空结算
 * liquidation	String	强平多/强平空/交割平多/交割平空
 */
public enum AccoutFlowSourceType {

    /**
     * 资金转入/转出
     */
    TRANSFER("transfer"),

    /**
     * 开多/开空/平多/平空
     */
    MATCH("match"),


    /**
     * 手续费
     */
    FEE("fee"),

    /**
     * 清算/分摊/多结算/空结算
     */
    SETTLEMENT("settlement"),

    /**
     * 资金费
     */
    FUNDING("funding"),


    /**
     * 强平多/强平空/交割平多/交割平空
     */
    LIQUIDATION("liquidation");


    private final String value;

    AccoutFlowSourceType(String value) {
        this.value = value;
    }

    @JsonCreator
    public static AccoutFlowSourceType fromValue(String value) {
        for (AccoutFlowSourceType type : AccoutFlowSourceType.values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("AccoutFlowSourceType 无效 ,value= " + value);
    }

    @JsonValue
    public String getValue() {
        return this.value;
    }

}
