package com.kevin.gateway.okexapi.base.websocket.response;

import lombok.Data;

/**
 * <pre>
 *      {"event":"login","success":true}
 *  </pre>
 */
@Data
public class EventSuccessResponse {
    private String event;
    private boolean success;
}
