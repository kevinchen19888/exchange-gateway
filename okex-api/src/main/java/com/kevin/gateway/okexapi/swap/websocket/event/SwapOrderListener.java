package com.kevin.gateway.okexapi.swap.websocket.event;


import com.kevin.gateway.okexapi.swap.websocket.response.OrderData;

public interface SwapOrderListener {
    void handleOrderData(OrderData orderData);
}
