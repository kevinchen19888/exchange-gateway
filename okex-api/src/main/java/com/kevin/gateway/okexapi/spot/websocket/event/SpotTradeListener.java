package com.kevin.gateway.okexapi.spot.websocket.event;

import com.kevin.gateway.okexapi.spot.vo.SpotTradeData;
import com.kevin.gateway.okexapi.spot.SpotMarketId;

public interface SpotTradeListener {
    void handleTradeData(SpotMarketId id, SpotTradeData tradeData);
}
