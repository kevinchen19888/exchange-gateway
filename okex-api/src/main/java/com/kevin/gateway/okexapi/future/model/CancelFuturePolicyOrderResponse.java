package com.kevin.gateway.okexapi.future.model;

import com.kevin.gateway.okexapi.future.FutureMarketId;
import com.kevin.gateway.okexapi.spot.model.SpotAlgoOrderType;
import lombok.Data;

@Data
public class CancelFuturePolicyOrderResponse extends ResultCode {


    /**
     * 合约ID
     */
    private FutureMarketId instrumentId;


    /**
     * 1：止盈止损
     * 2：跟踪委托
     * 3：冰山委托
     * 4：时间加权
     * 5：止盈止损
     */
    private SpotAlgoOrderType orderType;

    /**
     * 订单ID，下单失败时，此字段值为-1
     */

    private String algoIds;


}

