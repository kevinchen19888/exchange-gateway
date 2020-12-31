package com.kevin.gateway.okexapi.swap.websocket.event;

import com.kevin.gateway.okexapi.swap.vo.SwapPriceRangeData;
import com.kevin.gateway.okexapi.swap.SwapMarketId;

/**
 *  用户交易频道信息监听
 */
public interface SwapPriceRangeListener {
    void handlePriceRangeData(SwapMarketId id, SwapPriceRangeData priceRangeData);
}
