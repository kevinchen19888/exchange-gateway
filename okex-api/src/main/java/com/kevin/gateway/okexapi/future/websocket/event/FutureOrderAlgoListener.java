package com.kevin.gateway.okexapi.future.websocket.event;

import com.kevin.gateway.okexapi.future.websocket.response.OrderAlgoData;


public interface FutureOrderAlgoListener {
    void handleOrderAlgoData(OrderAlgoData orderAlgoData);
}
