package com.kevin.gateway.okexapi.future.model;

import com.kevin.gateway.okexapi.future.FutureMarketId;
import com.kevin.gateway.okexapi.future.type.DirectionType;
import com.kevin.gateway.okexapi.future.type.IncreaseDecreaseType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 增加/减少保证金  请求参数
 */
@Data
public class UpdateMarginRequest {

    /**
     * 合约ID，如BTC-USDT-200626
     */
    private FutureMarketId instrumentId;

    /**
     * 开仓方向，long(做多)或者short(做空)
     */
    private DirectionType direction;

    /**
     * 增加/减少：1：增加 2：减少
     */
    private IncreaseDecreaseType type;

    /**
     * 增加或减少的保证金数量
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal amount;

}

