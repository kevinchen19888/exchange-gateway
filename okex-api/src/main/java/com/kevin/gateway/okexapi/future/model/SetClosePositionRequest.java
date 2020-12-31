package com.kevin.gateway.okexapi.future.model;

import com.kevin.gateway.okexapi.future.FutureMarketId;
import com.kevin.gateway.okexapi.future.type.DirectionType;
import lombok.Data;

/**
 * 市价全平
 */
@Data
public class SetClosePositionRequest {

    /**
     * 合约ID，如BTC-USD-180213,BTC-USDT-191227
     */
    private FutureMarketId instrumentId;


    /**
     * 平仓方向
     * long:平多
     * short:平空
     */
    private DirectionType direction;

}

