package com.kevin.gateway.okexapi.spot.websocket.event;

import com.kevin.gateway.okexapi.base.util.DepthAction;
import com.kevin.gateway.okexapi.base.util.DepthEntries;
import com.kevin.gateway.okexapi.spot.SpotMarketId;
import com.kevin.gateway.okexapi.spot.vo.SpotDepthData;

public interface SpotDepthListener {
    void handleDepthData(SpotMarketId id, SpotDepthData spotDepthData, DepthEntries depthEntries, DepthAction depthAction);
}
