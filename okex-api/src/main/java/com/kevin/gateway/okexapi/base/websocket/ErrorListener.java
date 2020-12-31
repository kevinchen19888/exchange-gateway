package com.kevin.gateway.okexapi.base.websocket;

import com.kevin.gateway.okexapi.base.websocket.response.FailureResponse;

public interface ErrorListener {
    void handleErrorMessage(FailureResponse failureResponse);
}
