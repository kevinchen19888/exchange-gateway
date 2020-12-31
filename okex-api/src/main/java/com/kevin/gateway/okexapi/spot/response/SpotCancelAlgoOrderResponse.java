package com.kevin.gateway.okexapi.spot.response;

import com.kevin.gateway.core.CoinPair;
import com.kevin.gateway.okexapi.spot.model.SpotAlgoOrderType;
import lombok.Data;

import java.util.List;

/**
 * 委托策略撤单
 * 响应实体
 */
@Data
public class SpotCancelAlgoOrderResponse {
    /**
     * 撤销指定的币对
     */
    private CoinPair instrumentId;

    /**
     * 撤销指定的委托单ID
     */
    private List<String> algoIds;

    /**
     * 1：计划委托
     * 2：跟踪委托
     * 3：冰山委托
     * 4：时间加权
     * 5：止盈止损
     */
    private SpotAlgoOrderType orderType;

    /**
     * 调用接口返回结果
     */
    private String result;
    /**
     * 错误码，撤单成功时为0，撤单失败时会显示相应错误码
     */
    private String errorCode;
    /**
     * 错误信息，撤单成功时为空，撤单失败时会显示错误信息
     */
    private String errorMessage;
}
