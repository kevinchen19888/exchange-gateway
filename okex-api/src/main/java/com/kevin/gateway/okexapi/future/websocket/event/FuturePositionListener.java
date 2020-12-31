package com.kevin.gateway.okexapi.future.websocket.event;

import com.kevin.gateway.okexapi.future.websocket.response.PositionData;

public interface FuturePositionListener {
    void handleMarginAccountData(PositionData positionData);
}
