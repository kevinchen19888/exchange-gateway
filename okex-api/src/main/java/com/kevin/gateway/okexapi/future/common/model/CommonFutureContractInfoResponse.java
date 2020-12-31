package com.kevin.gateway.okexapi.future.common.model;


import com.kevin.gateway.core.Coin;
import com.kevin.gateway.core.CoinPair;
import com.kevin.gateway.okexapi.future.FutureMarketId;
import com.kevin.gateway.okexapi.future.common.type.FutureAliasType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CommonFutureContractInfoResponse {

    /**
     * 合约ID，如BTC-USD-180213,BTC-USDT-191227
     */
    private FutureMarketId instrumentId;

    /**
     * 标的指数，如：BTC
     */
    private Coin underlyingIndex;


    /**
     * 标的指数，如：BTC-USDT-1212
     */
    private CoinPair underlying;

    /**
     * 交易货币，如：BTC-USD中的BTC ,BTC-USDT中的BTC
     */
    private Coin baseCurrency;


    /**
     * 计价货币币种，如：BTC-USD中的USD ,BTC-USDT中的USDT
     */
    private Coin quoteCurrency;


    /**
     * 盈亏结算和保证金币种，如BTC
     */
    private Coin settlementCurrency;


    /**
     * 合约面值(美元)
     */
    private BigDecimal contractVal;


    /**
     * 上线日期
     */
    private LocalDate listing;


    /**
     * 交割日期
     */
    private LocalDate delivery;


    /**
     * 下单价格精度
     */
    private BigDecimal tickSize;


    /**
     * 下单数量精度
     */
    private BigDecimal tradeIncrement;


    /**
     * 本周 this_week
     * 次周 next_week
     * 季度 quarter
     * 次季度 bi_quarter
     */
    private FutureAliasType alias;


    /**
     * true or false ,是否 币本位保证金合约
     */
    @JsonProperty("is_inverse")
    private Boolean isInverse;


    /**
     * 合约面值计价币种 如 usd，btc，ltc，etc xrp eos
     */
    private Coin contractValCurrency;


    /**
     * 手续费档位
     */
    private Integer category;


}
