package com.kevin.gateway.okexapi.future.websocket.event;

import com.kevin.gateway.okexapi.future.vo.FutureInstrumentData;
import com.kevin.gateway.okexapi.future.FutureMarketId;

/**
 *  币币账户频道信息监听
 */
public interface FutureInstrumentListener {
    void handleInstrumentData(FutureMarketId id, FutureInstrumentData instrumentData);
}
