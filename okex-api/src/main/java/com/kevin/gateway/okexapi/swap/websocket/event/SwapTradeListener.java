package com.kevin.gateway.okexapi.swap.websocket.event;

import com.kevin.gateway.okexapi.swap.vo.SwapTradeData;
import com.kevin.gateway.okexapi.swap.SwapMarketId;

public interface SwapTradeListener {
    void handleTradeData(SwapMarketId id, SwapTradeData tradeData);
}
