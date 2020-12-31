package com.kevin.gateway.okexapi.spot.websocket.event;

import com.kevin.gateway.okexapi.base.util.CandleInterval;
import com.kevin.gateway.okexapi.spot.vo.SpotCandleData;
import com.kevin.gateway.okexapi.spot.SpotMarketId;

public interface SpotCandleListener {
    void handleCandleData(SpotMarketId id, CandleInterval candlePeriod, SpotCandleData candleData);
}
