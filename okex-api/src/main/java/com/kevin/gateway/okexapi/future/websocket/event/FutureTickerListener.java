package com.kevin.gateway.okexapi.future.websocket.event;

import com.kevin.gateway.okexapi.future.FutureMarketId;
import com.kevin.gateway.okexapi.future.vo.FutureTickerData;

public interface FutureTickerListener {
    void handleTickerData(FutureMarketId id, FutureTickerData tickerData);
}
