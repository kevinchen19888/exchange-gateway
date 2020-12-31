package com.kevin.gateway.okexapi.swap.model;

import com.kevin.gateway.okexapi.future.model.ResultCode;
import com.kevin.gateway.okexapi.future.type.DirectionType;
import com.kevin.gateway.okexapi.swap.SwapMarketId;
import lombok.Data;

@Data
public class SetSwapPositionResponse extends ResultCode {

    /**
     * 合约ID，如BTC-USD-180213,BTC-USDT-191227
     */
    private SwapMarketId instrumentId;


    /**
     * 平仓方向
     * long:平多
     * short:平空
     */
    private DirectionType direction;


}

