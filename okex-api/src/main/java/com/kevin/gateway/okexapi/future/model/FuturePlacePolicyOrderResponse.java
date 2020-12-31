package com.kevin.gateway.okexapi.future.model;

import com.kevin.gateway.okexapi.future.FutureMarketId;

import com.kevin.gateway.okexapi.spot.model.SpotAlgoOrderType;
import com.kevin.gateway.okexapi.spot.model.SpotPlaceAlgoType;
import lombok.Data;

@Data
public class FuturePlacePolicyOrderResponse extends ResultCode {


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

    private String algoId;

    /**
     * 1:限价 2:市场价；触发价格类型，默认是1:限价，为2:市场价时，委托价格不必填；
     */
    private SpotPlaceAlgoType algoType;


}

