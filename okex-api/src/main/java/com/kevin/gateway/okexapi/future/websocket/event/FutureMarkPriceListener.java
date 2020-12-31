package com.kevin.gateway.okexapi.future.websocket.event;

import com.kevin.gateway.okexapi.future.FutureMarketId;
import com.kevin.gateway.okexapi.future.vo.FutureMarkPriceData;

public interface FutureMarkPriceListener {
    void handleMarketPriceData(FutureMarketId id, FutureMarkPriceData markPriceData);
}
