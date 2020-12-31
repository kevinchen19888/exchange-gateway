package com.kevin.gateway.okexapi.swap.model;


import com.kevin.gateway.okexapi.spot.model.SpotAlgoOrderType;
import com.kevin.gateway.okexapi.swap.SwapMarketId;
import lombok.Data;

@Data
public class SwapPlaceAlgoOrderResponseData {
    /**
     * 合约ID
     */
    private SwapMarketId instrumentId;


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
     * 错误码，请求成功时为0，请求失败时会显示相应错误码
     */
    private String errorCode;

    /**
     * 错误信息，请求成功时为空，请求失败时会显示错误信息
     */
    private String errorMessage;

    /**
     * 接口调用返回结果，success
     */
    private String result;

}
