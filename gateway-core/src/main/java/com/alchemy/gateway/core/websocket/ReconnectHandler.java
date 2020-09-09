package com.alchemy.gateway.core.websocket;

import org.springframework.web.socket.CloseStatus;

/**
 * 重连处理器
 */
public interface ReconnectHandler {

    void reconnect(CloseStatus closeStatus);
}
