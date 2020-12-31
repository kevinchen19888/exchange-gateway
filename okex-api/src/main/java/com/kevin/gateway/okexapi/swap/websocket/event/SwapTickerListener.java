package com.kevin.gateway.okexapi.swap.websocket.event;

import com.kevin.gateway.okexapi.swap.vo.SwapTickerData;
import com.kevin.gateway.okexapi.swap.SwapMarketId;

public interface SwapTickerListener {
    void handleTickerData(SwapMarketId id, SwapTickerData tickerData);
}
