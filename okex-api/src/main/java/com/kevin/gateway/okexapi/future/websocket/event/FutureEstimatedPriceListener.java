package com.kevin.gateway.okexapi.future.websocket.event;

import com.kevin.gateway.okexapi.future.vo.FutureEstimatedPriceData;
import com.kevin.gateway.okexapi.future.FutureMarketId;

public interface FutureEstimatedPriceListener {
    void handleEstimatedPriceData(FutureMarketId id, FutureEstimatedPriceData estimatedPriceData);
}
