package com.kevin.gateway.okexapi.future.websocket.event;

import com.kevin.gateway.okexapi.future.websocket.response.OrderData;


public interface FutureOrderListener {
    void handleOrderData(OrderData orderData);
}
