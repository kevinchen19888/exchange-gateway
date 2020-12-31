package com.kevin.gateway.okexapi.option.websocket.event;

import com.kevin.gateway.okexapi.option.vo.OptionTickerData;
import com.kevin.gateway.okexapi.option.OptionMarketId;

public interface OptionTickerListener {
    void handleTickerData(OptionMarketId id, OptionTickerData tickerData);
}
