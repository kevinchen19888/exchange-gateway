package com.kevin.gateway.okexapi.spot.websocket.event;

import com.kevin.gateway.okexapi.spot.vo.SpotTickerData;
import com.kevin.gateway.okexapi.spot.SpotMarketId;

public interface SpotTickerListener {
    void handleTickerData(SpotMarketId id, SpotTickerData tickerData);
}
