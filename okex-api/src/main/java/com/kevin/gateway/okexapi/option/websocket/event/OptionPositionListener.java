package com.kevin.gateway.okexapi.option.websocket.event;

import com.kevin.gateway.okexapi.option.websocket.response.PositionData;

public interface OptionPositionListener {
    void handlePositionData(PositionData positionData);
}
