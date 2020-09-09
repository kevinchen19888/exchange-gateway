package com.alchemy.gateway.core.websocket;

import org.springframework.web.socket.BinaryMessage;

/**
 * 压缩（解压缩） BinaryMessage 的接口
 */
public interface BinaryMessageCompression {
    BinaryMessage compressToBinaryMessage(String s);

    String decompressFromBinaryMessage(BinaryMessage binaryMessage);
}
