package com.kevin.gateway.okexapi.base.websocket;

import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.TextMessage;

/**
 * WebSocket响应流过滤器
 */
public interface WebSocketStreamFilter {
    /**
     * 过滤消息
     *
     * @param message 接收到的二进制流
     * @return 文本流
     */
    TextMessage doFilter(BinaryMessage message);
}
