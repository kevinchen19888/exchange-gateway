package com.kevin.gateway.okexapi.option.websocket.event;

import com.kevin.gateway.okexapi.base.util.DepthAction;
import com.kevin.gateway.okexapi.base.util.DepthEntries;
import com.kevin.gateway.okexapi.option.vo.OptionDepthData;
import com.kevin.gateway.okexapi.option.OptionMarketId;

public interface OptionDepthListener {
    void handleDepthData(OptionMarketId id, OptionDepthData depthData, DepthEntries depthEntries, DepthAction depthAction);
}
