package com.kevin.gateway.okexapi.future.vo;

import com.kevin.gateway.core.Coin;
import com.kevin.gateway.core.FiatCoin;
import com.kevin.gateway.okexapi.future.FutureMarketId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 *{
 *     "table":"future/instruments",
 *     "data":[
 *         [
 *             {
 *                 "instrument_id":"BTC-USD-191115",
 *                 "underlying_index":"BTC",
 *                 "quote_currency":"USD",
 *                 "tick_size":"0.01",
 *                 "contract_val":"100",
 *                 "listing":"2019-11-01",
 *                 "delivery":"2019-11-15",
 *                 "trade_increment":"1",
 *                 "alias":"next_week",
 *                 "underlying":"BTC-USD",
 *                 "base_currency":"BTC",
 *                 "settlement_currency":"BTC",
 *                 "is_inverse":"true",
 *                 "contract_val_currency":"USD"
 *             },
 *             {
 *                 "instrument_id":"TRX-USD-191115",
 *                 "underlying_index":"TRX",
 *                 "quote_currency":"USD",
 *                 "tick_size":"0.00001",
 *                 "contract_val":"10",
 *                 "listing":"2019-11-01",
 *                 "delivery":"2019-11-15",
 *                 "trade_increment":"1",
 *                 "alias":"next_week",
 *                 "underlying":"TRX-USD",
 *                 "base_currency":"TRX",
 *                 "settlement_currency":"TRX",
 *                 "is_inverse":"true",
 *                 "contract_val_currency":"USD"
 *             }
 *         ]
 *     ]
 * }
 */
@Data
public class FutureInstrumentData {
    /**
     *  合约ID，如BTC-USD-190322 ,BTC-USDT-191227
     */
    private FutureMarketId instrumentId;

    /**
     *  	标的指数，如：BTC-USD
     */
    private String underlyingIndex;


    /**
     *  计价货币币种，如：BTC-USD-190322中的USD
     */
    @JsonProperty("quote_currency")
    private FiatCoin quoteCoin;

    /**
     * 下单价格精度：（对应okex的tick_size）
     */
    @JsonProperty("tick_size")
    private BigDecimal pricePrecision;

    /**
     * 合约面值(美元)（对应okex的contract_val）
     */
    @JsonProperty("contract_val")
    private BigDecimal contractValue;

    /**
     * 上线日期：（对应okex的listing）
     */
    @JsonProperty("listing")
    private LocalDate listingDate;

    /**
     * 交割日期：（对应okex的delivery
     */
    @JsonProperty("delivery")
    private LocalDate deliveryDate;

    /**
     * 下单数量精度：（对应okex的trade_increment）
     */
    @JsonProperty("trade_increment")
    private int volumePrecision;

    private String alias;

    /**
     * 标的指数，如：BTC-USD
     */
    private String underlying;

    /**
     * 交易货币币中（对应okex的base_currency）
     */
    @JsonProperty("base_currency")
    private Coin baseCoin;

    /**
     * 盈亏结算和保证金币种（对应okex的settlement_currency），如BTC
     */
    @JsonProperty("settlement_currency")
    private Coin settlementCoin;

    /**
     * 是否 币本位保证金合约：（对应okex的is_inverse）
     */
    @JsonProperty("is_inverse")
    private boolean isInverse;

    /**
     * 合约面值计价币种 如 USD,BTC,LTC,ETC,XRP,EOS（对应okex的contract_val_currency）
     */
    @JsonProperty("contract_val_currency")
    private Coin contractValueCoin;
}
