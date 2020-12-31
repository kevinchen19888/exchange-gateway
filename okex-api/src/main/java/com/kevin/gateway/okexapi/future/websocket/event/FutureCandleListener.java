package com.kevin.gateway.okexapi.future.websocket.event;

import com.kevin.gateway.okexapi.base.util.CandleInterval;
import com.kevin.gateway.okexapi.future.vo.FutureCandleData;
import com.kevin.gateway.okexapi.future.FutureMarketId;

public interface FutureCandleListener {
    void handleCandleData(FutureMarketId id, CandleInterval candlePeriod, FutureCandleData candleData);
}
