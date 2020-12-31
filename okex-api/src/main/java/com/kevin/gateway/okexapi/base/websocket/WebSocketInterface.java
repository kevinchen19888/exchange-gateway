package com.kevin.gateway.okexapi.base.websocket;

import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Optional;

public interface WebSocketInterface {
    /**
     * 发起连接
     */
    void connect();

    /**
     * 断开连接
     *
     * @throws IOException 断开连接时出错
     */
    void disconnect() throws IOException;

    /**
     * 判断是否已经建立连接
     *
     * @return true 已连接
     */
    boolean isConnected();

    /**
     * 获取底层 WebSocketSession 对象
     *
     * @return 返回可选的 WebSocketSession 对象
     */
    Optional<WebSocketSession> getWebSocketSession();
}
