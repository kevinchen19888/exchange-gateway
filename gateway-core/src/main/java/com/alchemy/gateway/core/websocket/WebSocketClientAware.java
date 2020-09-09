package com.alchemy.gateway.core.websocket;

import org.springframework.web.socket.client.WebSocketClient;

/**
 * 可注入 WebSocketClient 接口
 */
public interface WebSocketClientAware {
    /**
     * WebSocket 客户端接口
     *
     * @param webSocketClient WebSocket 客户端接口
     */
    void setWebSocketClient(WebSocketClient webSocketClient);
}
