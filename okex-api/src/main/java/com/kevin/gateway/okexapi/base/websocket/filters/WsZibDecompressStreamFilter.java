package com.kevin.gateway.okexapi.base.websocket.filters;

import com.kevin.gateway.okexapi.base.websocket.WebSocketStreamFilter;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.TextMessage;

import java.nio.charset.StandardCharsets;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

/**
 * WebSocket 流 ZLib 解压缩过滤器类
 */
public class WsZibDecompressStreamFilter implements WebSocketStreamFilter {
    @Override
    public TextMessage doFilter(BinaryMessage message) {

        // TODO: 需要验证
        try {
            Inflater inflater = new Inflater(true);
            inflater.setInput(message.getPayload().array(), 0, message.getPayloadLength());
            StringBuilder sb = new StringBuilder();
            byte[] buffer = new byte[1024];
            while (!inflater.finished()) {
                int inflateResultLength = inflater.inflate(buffer);
                sb.append(new String(buffer, 0, inflateResultLength, StandardCharsets.UTF_8));
            }
            inflater.end();
            return new TextMessage(sb.toString());
        } catch (DataFormatException e) {
            throw new IllegalStateException("WebSocket 解压缩失败", e);
        }
    }
}
