package com.kevin.gateway.okexapi.index.websocket.event;

import com.kevin.gateway.okexapi.index.vo.IndexTickerData;
import com.kevin.gateway.okexapi.index.IndexInstrumentId;

public interface IndexTickerListener {
    void handleTickerData(IndexInstrumentId id, IndexTickerData tickerData);
}
