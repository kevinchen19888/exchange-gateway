package com.kevin.gateway.okexapi.swap.vo;

import com.kevin.gateway.core.Coin;
import com.kevin.gateway.core.FiatCoin;
import com.kevin.gateway.okexapi.swap.SwapMarketId;
import com.kevin.gateway.okexapi.swap.type.SwapCategoryType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 *{
 *     "table":"swap/instruments",
 *     "data":[
 *     {
 *         "instrument_id":"BTC-USD-SWAP",
 *         "underlying_index":"BTC",
 *         "quote_currency":"USD",
 *         "coin":"BTC",
 *         "contract_val":"100",
 *         "listing":"2018-08-28T02:43:23.000Z",
 *         "delivery":"2019-11-26T08:00:00.000Z",
 *         "size_increment":"1",
 *         "tick_size":"0.1",
 *         "base_currency":"BTC",
 *         "underlying":"BTC-USD",
 *         "settlement_currency":"BTC",
 *         "is_inverse":"true",
 *         "category":"1",
 *         "contract_val_currency":"USD"
 *     },
 *     {
 *         "instrument_id":"LTC-USD-SWAP",
 *         "underlying_index":"LTC",
 *         "quote_currency":"USD",
 *         "coin":"LTC",
 *         "contract_val":"10",
 *         "listing":"2018-12-21T07:53:47.000Z",
 *         "delivery":"2019-11-26T08:00:00.000Z",
 *         "size_increment":"1",
 *         "tick_size":"0.01",
 *         "base_currency":"LTC",
 *         "underlying":"LTC-USD",
 *         "settlement_currency":"LTC",
 *         "is_inverse":"true",
 *         "category":"1",
 *         "contract_val_currency":"USD"
 *     }
 * ]
 * }
 */

@Data
public class SwapInstrumentData {
    /**
     *  合约ID，BTC-USD-SWAP
     */
    private SwapMarketId instrumentId;

    /**
     *  	标的指数，如：BTC-USD
     */
    private String underlying_Index;

    /**
     *  币种
     */
    @JsonProperty("coin")
    private Coin coin;

    /**
     *  币种，如BTC
     */
    @JsonProperty("base_currency")
    private Coin baseCoin;

    /**
     *  计价货币币种，如：BTC-USD-190322中的USD
     */
    @JsonProperty("quote_currency")
    private FiatCoin quoteCoin;

    /**
     * 	盈亏结算和保证金币种，BTC
     */
    @JsonProperty("settlement_currency")
    private Coin settlementCoin;

    /**
     * 合约面值(美元)（对应okex的contract_val）
     */
    @JsonProperty("contract_val")
    private BigDecimal contractValue;

    /**
     * 上线日期：（对应okex的listing）
     */
    @JsonProperty("listing")
    private LocalDateTime listingDate;

    /**
     * 交割日期：（对应okex的delivery
     */
    @JsonProperty("delivery")
    private LocalDateTime deliveryDateTime;

    /**
     * 下单数量精度：（对应okex的size_increment）
     */
    @JsonProperty("size_increment")
    private int volumePrecision;

    /**
     * 标的指数，如：BTC-USD
     */
    private String underlying;

    /**
     * 下单价格精度：（对应okex的tick_size）
     */
    @JsonProperty("tick_size")
    private BigDecimal pricePrecision;

    /**
     * 是否 币本位保证金合约：（对应okex的is_inverse）
     */
    private Boolean isInverse;

    /**
     * 手续费档位，1：第一档，2：第二档
     */
    private SwapCategoryType category;

    /**
     * 合约面值计价币种 如 USD,BTC,LTC,ETC,XRP,EOS（对应okex的contract_val_currency）
     */
    @JsonProperty("contract_val_currency")
    private Coin contractValueCoin;
}
