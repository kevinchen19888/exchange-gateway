package com.kevin.gateway.okexapi.option.websocket.event;

import com.kevin.gateway.okexapi.option.vo.OptionSummaryData;
import com.kevin.gateway.okexapi.option.OptionMarketId;

public interface OptionSummaryListener {
    void handleSummaryData(OptionMarketId id, OptionSummaryData summaryData);
}
