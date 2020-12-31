package com.kevin.gateway.okexapi.option.websocket.event;

import com.kevin.gateway.okexapi.option.vo.OptionTradeData;
import com.kevin.gateway.okexapi.option.OptionMarketId;

public interface OptionTradeListener {
    void handleTradeData(OptionMarketId id, OptionTradeData tradeData);
}
