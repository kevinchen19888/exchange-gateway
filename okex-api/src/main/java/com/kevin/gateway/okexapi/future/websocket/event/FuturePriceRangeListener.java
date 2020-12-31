package com.kevin.gateway.okexapi.future.websocket.event;

import com.kevin.gateway.okexapi.future.vo.FuturePriceRangeData;
import com.kevin.gateway.okexapi.future.FutureMarketId;

/**
 *  用户交易频道信息监听
 */
public interface FuturePriceRangeListener {
    void handlePriceRangeData(FutureMarketId id, FuturePriceRangeData priceRangeData);
}
