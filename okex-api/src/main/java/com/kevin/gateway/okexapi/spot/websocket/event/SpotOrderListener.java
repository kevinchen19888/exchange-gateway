package com.kevin.gateway.okexapi.spot.websocket.event;

import com.kevin.gateway.okexapi.spot.websocket.response.OrderData;


public interface SpotOrderListener {
    void handleOrderData(OrderData orderData);
}
