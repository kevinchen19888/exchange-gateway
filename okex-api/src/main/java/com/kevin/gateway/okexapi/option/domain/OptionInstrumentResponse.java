package com.kevin.gateway.okexapi.option.domain;

import com.kevin.gateway.core.Coin;
import com.kevin.gateway.core.CoinPair;
import com.kevin.gateway.okexapi.base.util.OkexFeeCategory;
import com.kevin.gateway.okexapi.option.OptionMarketId;
import com.kevin.gateway.okexapi.option.util.ContractStatus;
import com.kevin.gateway.okexapi.option.util.OptionType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 公共-获取期权合约
 */
@Data
public class OptionInstrumentResponse {
    /**
     * 合约ID，如BTC-USD-190830-9000-C
     */
    private OptionMarketId instrumentId;
    /**
     * 标的指数，如BTC-USD
     */
    private CoinPair underlying;
    /**
     * 盈亏结算和保证金币种，如BTC
     */
    private Coin settlementCurrency;
    /**
     * 合约乘数，对BTC期权合约，该值为0.1
     */
    private BigDecimal contractVal;
    /**
     * 期权类型，C或P
     */
    private OptionType optionType;
    /**
     * 行权价格
     */
    private BigDecimal strike;
    /**
     * 价格精度（如0.0005）
     */
    private BigDecimal tickSize;
    /**
     * 一手合约张数
     */
    private BigDecimal lotSize;
    /**
     * 上线日期，ISO 8601格式
     */
    private LocalDateTime listing;
    /**
     * 交割日期，ISO 8601格式
     */
    private LocalDateTime delivery;
    /**
     * 合约状态：1-预上线, 2-已上线, 3-暂停交易, 4-结算中
     */
    private ContractStatus state;
    /**
     * 手续费档位
     */
    private OkexFeeCategory category;
    /**
     * 交易开始时间，ISO 8601格式
     */
    private LocalDateTime tradingStartTime;
    /**
     * 合约状态变更时间，ISO 8601格式
     */
    private LocalDateTime timestamp;
}
