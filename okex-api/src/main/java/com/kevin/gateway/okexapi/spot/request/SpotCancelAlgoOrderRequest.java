package com.kevin.gateway.okexapi.spot.request;

import com.kevin.gateway.core.CoinPair;
import com.kevin.gateway.okexapi.spot.model.SpotAlgoOrderType;
import lombok.Data;

import java.util.List;

/**
 * 委托策略撤单
 */
@Data
public class SpotCancelAlgoOrderRequest {
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
}
