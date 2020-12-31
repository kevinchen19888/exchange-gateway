package com.kevin.gateway.okexapi.future.model;

import com.kevin.gateway.okexapi.future.FutureMarketId;
import com.kevin.gateway.okexapi.future.type.DirectionType;
import com.kevin.gateway.okexapi.future.type.IncreaseDecreaseType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateMarginResponse {

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
    private BigDecimal amount;


    /**
     * 增加/减少后的最新强平价格
     */
    private BigDecimal liquidationPrice;

    /**
     * 返回设定结果，成功或错误码
     */
    private boolean result;

}

