package com.kevin.gateway.okexapi.index.websocket.event;

import com.kevin.gateway.okexapi.base.util.CandleInterval;
import com.kevin.gateway.okexapi.index.vo.IndexCandleData;
import com.kevin.gateway.okexapi.index.IndexInstrumentId;

public interface IndexCandleListener {
    void handleCandleData(IndexInstrumentId id, CandleInterval candlePeriod, IndexCandleData candleData);
}
