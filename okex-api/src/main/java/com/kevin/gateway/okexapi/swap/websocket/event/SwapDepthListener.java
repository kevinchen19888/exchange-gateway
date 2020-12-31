package com.kevin.gateway.okexapi.swap.websocket.event;

import com.kevin.gateway.okexapi.base.util.DepthAction;
import com.kevin.gateway.okexapi.base.util.DepthEntries;
import com.kevin.gateway.okexapi.swap.SwapMarketId;
import com.kevin.gateway.okexapi.swap.vo.SwapDepthData;

public interface SwapDepthListener {
    void handleDepthData(SwapMarketId id, SwapDepthData depthData, DepthEntries depthEntries, DepthAction depthAction);
}
