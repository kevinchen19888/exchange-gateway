package com.kevin.gateway.okexapi.swap.model;

import com.kevin.gateway.okexapi.spot.model.SpotAlgoOrderType;
import com.kevin.gateway.okexapi.swap.SwapMarketId;
import lombok.Data;

import java.util.List;

/**
 * 委托策略撤单
 */
@Data
public class SwapCancelAlgoOrderRequest {
    /**
     * 撤销指定的币对
     */
    private SwapMarketId instrumentId;

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
