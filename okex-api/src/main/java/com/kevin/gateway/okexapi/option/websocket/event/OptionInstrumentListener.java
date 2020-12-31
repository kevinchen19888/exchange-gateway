package com.kevin.gateway.okexapi.option.websocket.event;

import com.kevin.gateway.okexapi.option.vo.OptionInstrumentData;
import com.kevin.gateway.okexapi.option.OptionMarketId;

/**
 *  币币账户频道信息监听
 */
public interface OptionInstrumentListener {
    void handleInstrumentData(OptionMarketId id, OptionInstrumentData instrumentData);
}
