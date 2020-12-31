package com.kevin.gateway.okexapi.spot.response;

import com.kevin.gateway.core.CoinPair;
import com.kevin.gateway.okexapi.spot.model.SpotAlgoOrderType;
import lombok.Data;

/**
 * 委托策略下单
 * 响应实体
 */
@Data
public class SpotPlaceAlgoOrderResponse {
    /**
     * 调用接口返回结果
     */
    private String result;
    /**
     * 订单ID，下单失败时，此字段值为-1
     */
    private String algoId;
    /**
     * 错误码，下单成功时为0，下单失败时会显示相应错误码
     */
    private String errorCode;
    /**
     * 错误信息，下单成功时为空，下单失败时会显示错误信息
     */
    private String errorMessage;

    /**
     * 币对
     */
    private CoinPair instrumentId;

    /**
     * 订单类型
     */
    private SpotAlgoOrderType orderType;

}
