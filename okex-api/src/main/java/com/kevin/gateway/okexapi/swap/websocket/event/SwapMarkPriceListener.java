package com.kevin.gateway.okexapi.swap.websocket.event;

import com.kevin.gateway.okexapi.swap.vo.SwapMarkPriceData;
import com.kevin.gateway.okexapi.swap.SwapMarketId;

public interface SwapMarkPriceListener {
    void handleMarkPriceData(SwapMarketId id, SwapMarkPriceData markPriceData);
}
