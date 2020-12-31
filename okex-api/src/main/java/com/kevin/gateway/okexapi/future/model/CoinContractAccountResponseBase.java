package com.kevin.gateway.okexapi.future.model;

import com.kevin.gateway.core.Coin;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 所有币种合约账户信息  公共部分
 * "can_withdraw":"0.00169207",
 * "currency":"BCH",
 * "equity":"0.00169207",
 * "liqui_mode":"tier",
 * "margin_mode":"fixed",
 * "total_avail_balance":"0.00169207"
 */

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "margin_mode")// name属性指定了json序列化&反序列化时多态的判断
@JsonSubTypes({
        @JsonSubTypes.Type(value = CoinContractAccountResponseCrossed.class, name = "crossed"),
        @JsonSubTypes.Type(value = CoinContractAccountResponseFixed.class, name = "fixed")

})

@Data
public class CoinContractAccountResponseBase {

    /**
     * 强平模式：tier（梯度强平）
     */
    private String liquiMode;

    /**
     * 可划转数量
     */
    private BigDecimal canWithdraw;

    /**
     * 账户余额币种
     */
    private Coin currency;


//    private String marginMode ;

    /**
     * 账户权益
     */
    private BigDecimal equity;

    /**
     * 账户余额
     */
    private BigDecimal totalAvailBalance;

}

