package com.kevin.gateway.okexapi.fundingaccount.domain;

import com.kevin.gateway.okexapi.fundingaccount.util.CurrencyVoDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 币种列表信息Vo
 */
@Data
public class CurrencyResponse {
    /**
     * 币种名称，如btc
     */
    private String currency;// TODO: 2020/9/21 待确认币种格式
    /**
     * 币种中文名称，不显示则无对应名称
     */
    private String name;
    /**
     * 是否可充值，0表示不可充值，1表示可以充值
     */
    @JsonDeserialize(using = CurrencyVoDeserializer.class)
    private Boolean canDeposit;
    /**
     * 是否可提币，0表示不可提币，1表示可以提币
     */
    @JsonDeserialize(using = CurrencyVoDeserializer.class)
    private Boolean canWithdraw;
    /**
     * 币种最小提币量
     */
    private BigDecimal minWithdrawal;
}
