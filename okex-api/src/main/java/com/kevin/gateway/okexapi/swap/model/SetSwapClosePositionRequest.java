package com.kevin.gateway.okexapi.swap.model;

import com.kevin.gateway.okexapi.future.type.DirectionType;
import com.kevin.gateway.okexapi.swap.SwapMarketId;
import lombok.Data;

/**
 * 市价全平
 */
@Data
public class SetSwapClosePositionRequest {

    /**
     * 合约ID，如BTC-USD-SWAP
     */
    private SwapMarketId instrumentId;


    /**
     * 平仓方向
     * long:平多
     * short:平空
     */
    private DirectionType direction;

}

