package com.alchemy.gateway.core.websocket;

import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

import java.io.IOException;

/**
 * 支持压缩格式的二进制消息 WebSocket 处理器接口
 */
public abstract class CompressionWebSocketHandler extends BinaryWebSocketHandler {
    private final BinaryMessageCompression compression;

    public CompressionWebSocketHandler(BinaryMessageCompression compression) {
        this.compression = compression;
    }

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {
        // 将压缩过的二进制消息解压缩并转换为文本消息
        String decompressedString = compression.decompressFromBinaryMessage(message);
        TextMessage decompressedMessage = new TextMessage(decompressedString);
        handleDecompressBinaryMessage(session, decompressedMessage);
    }

    /**
     * 发送未压缩的文本消息
     *
     * @param session   WebSocket 会话
     * @param rawString 原始字符串
     * @throws IOException 可能抛出 IO 异常
     */
    protected void sendUncompressedString(WebSocketSession session, String rawString) throws IOException {
        BinaryMessage binaryMessage = compression.compressToBinaryMessage(rawString);
        session.sendMessage(binaryMessage);
    }

    /**
     * 子类需要重载此方法
     *
     * @param decompressedMessage 解压缩后的文本消息
     */
    protected abstract void handleDecompressBinaryMessage(WebSocketSession session, TextMessage decompressedMessage);
}
