package com.kevin.gateway.okexapi.option.websocket.event;

import com.kevin.gateway.okexapi.base.util.CandleInterval;
import com.kevin.gateway.okexapi.option.vo.OptionCandleData;
import com.kevin.gateway.okexapi.option.OptionMarketId;

public interface OptionCandleListener {
    void handleCandleData(OptionMarketId id, CandleInterval candlePeriod, OptionCandleData candleData);
}
