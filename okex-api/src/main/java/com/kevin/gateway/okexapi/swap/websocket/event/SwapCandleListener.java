package com.kevin.gateway.okexapi.swap.websocket.event;

import com.kevin.gateway.okexapi.base.util.CandleInterval;
import com.kevin.gateway.okexapi.swap.vo.SwapCandleData;
import com.kevin.gateway.okexapi.swap.SwapMarketId;

public interface SwapCandleListener {
    void handleCandleData(SwapMarketId id, CandleInterval candlePeriod, SwapCandleData candleData);
}
