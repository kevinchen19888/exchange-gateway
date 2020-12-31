package com.kevin.gateway.okexapi.swap.websocket.event;


import com.kevin.gateway.okexapi.swap.websocket.response.OrderAlgoData;

public interface SwapOrderAlgoListener {
    void handleOrderAlgoData(OrderAlgoData orderAlgoData);
}
