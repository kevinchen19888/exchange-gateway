package com.kevin.gateway.okexapi.swap.websocket.event;

import com.kevin.gateway.okexapi.swap.vo.SwapFundingRateData;
import com.kevin.gateway.okexapi.swap.SwapMarketId;

public interface SwapFundingRateListener {
    void handleFindingRateData(SwapMarketId id, SwapFundingRateData fundingRateData);
}
