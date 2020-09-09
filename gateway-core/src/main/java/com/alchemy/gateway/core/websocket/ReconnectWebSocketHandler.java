package com.alchemy.gateway.core.websocket;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;

public class ReconnectWebSocketHandler extends WebSocketHandlerDecorator {

    private ReconnectHandler reconnectHandler = null;

    public ReconnectWebSocketHandler(WebSocketHandler delegate) {
        super(delegate);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        super.afterConnectionClosed(session, closeStatus);

        if (reconnectHandler != null) {
            reconnectHandler.reconnect(closeStatus);
        }
    }

    public void setReconnectHandler(ReconnectHandler reconnectHandler) {
        this.reconnectHandler = reconnectHandler;
    }
}
