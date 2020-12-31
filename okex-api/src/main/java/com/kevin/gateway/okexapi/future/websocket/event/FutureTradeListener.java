package com.kevin.gateway.okexapi.future.websocket.event;

import com.kevin.gateway.okexapi.future.FutureMarketId;
import com.kevin.gateway.okexapi.future.vo.FutureTradeData;

public interface FutureTradeListener {
    void handleTradeData(FutureMarketId id, FutureTradeData tradeData);
}
