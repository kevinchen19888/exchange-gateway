package com.kevin.gateway.okexapi.future.websocket.event;

import com.kevin.gateway.okexapi.base.util.DepthAction;
import com.kevin.gateway.okexapi.base.util.DepthEntries;
import com.kevin.gateway.okexapi.future.FutureMarketId;
import com.kevin.gateway.okexapi.future.vo.FutureDepthData;

public interface FutureDepthListener {
    void handleDepthData(FutureMarketId id, FutureDepthData futureDepthData, DepthEntries depthEntries, DepthAction depthAction);
}
