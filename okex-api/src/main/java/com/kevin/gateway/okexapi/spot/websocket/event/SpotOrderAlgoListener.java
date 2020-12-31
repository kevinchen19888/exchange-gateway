package com.kevin.gateway.okexapi.spot.websocket.event;

import com.kevin.gateway.okexapi.spot.websocket.response.OrderAlgoData;

public interface SpotOrderAlgoListener {
    void handleOrderAlgoData(OrderAlgoData orderAlgoData);
}
