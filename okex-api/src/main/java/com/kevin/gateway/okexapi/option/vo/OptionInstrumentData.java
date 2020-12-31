package com.kevin.gateway.okexapi.option.vo;

import com.kevin.gateway.core.Coin;
import com.kevin.gateway.okexapi.option.OptionMarketId;
import com.kevin.gateway.okexapi.option.util.ContractStatus;
import com.kevin.gateway.okexapi.option.util.OptionType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 *{{
 *     "table": "option/instruments",
 *     "data": [{
 *             "instrument_id": "BTC-USD-190927-8500-P",
 *             "underlying": "BTC-USD",
 *             "settlement_currency": "BTC",
 *             "contract_val": "0.1000",
 *             "option_type": "P",
 *             "strike": "8500",
 *             "tick_size": "8",
 *             "lot_size": "1.0000",
 *             "listing": "2019-08-28T08:00:00.000Z",
 *             "delivery": "2019-09-27T08:00:00.000Z",
 *             "state": "2",
 *             "trading_start_time": "2019-08-28T07:30:39.678Z"
 *         },
 *         {
 *             "instrument_id": "BTC-USD-190927-8500-C",
 *             "underlying": "BTC-USD",
 *             "settlement_currency": "BTC",
 *             "contract_val": "0.1000",
 *             "option_type": "C",
 *             "strike": "8500",
 *             "tick_size": "8",
 *             "lot_size": "1.0000",
 *             "listing": "2019-08-28T08:00:00.000Z",
 *             "delivery": "2019-09-27T08:00:00.000Z",
 *             "state": "2",
 *             "trading_start_time": "2019-08-28T07:30:39.678Z"
 *         }
 *     ]
 * }
 */
@Data
public class OptionInstrumentData {
    /**
     *  合约ID，如BTC-USD-190830-9000-C
     */
    private OptionMarketId instrumentId;

    /**
     *  标的指数，如BTC-USD
     */
    private String underlying;

    /**
     *  盈亏结算和保证金币种，如BTC
     */
    @JsonProperty("settlement_currency")
    private Coin settlementCoin;

    /**
     * 合约乘数，对BTC期权合约，该值为0.1
     */
    private BigDecimal contractVal;

   /**
     * 	期权类型，C或P
     */
    private OptionType optionType;

    /**
     * 	行权价格
     */
    private BigDecimal strike;

    /**
     * 	价格精度（如0.0005）
     */
    private BigDecimal tickSize;

    /**
     * 一手合约张数
     */
    private BigDecimal lotSize;

    /**
     *  上线日期，ISO 8601格式
     */
    @JsonProperty("listing")
    private LocalDateTime listingDateTime;

    /**
     * 	交割日期，ISO 8601格式
     */
    @JsonProperty("delivery")
    private String deliveryDateTime;

    /**
     * 	合约状态：1-预上线, 2-已上线, 3-暂停交易, 4-结算中
     */
    private ContractStatus state;

    /**
     * 交易开始时间，ISO 8601格式
     */
    private LocalDateTime tradingStartTime;

    /**
     * 合约状态变更时间，ISO 8601格式
     */
    private LocalDateTime timestamp;

}
