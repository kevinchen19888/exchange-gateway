package com.kevin.gateway.okexapi.swap.websocket.event;


import com.kevin.gateway.okexapi.swap.websocket.response.PositionData;

public interface SwapPositionListener {
    void handlePositionData(PositionData positionData);
}
