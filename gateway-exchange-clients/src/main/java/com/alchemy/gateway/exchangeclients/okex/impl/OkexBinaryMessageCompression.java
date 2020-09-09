package com.alchemy.gateway.exchangeclients.okex.impl;

import com.alchemy.gateway.core.websocket.BinaryMessageCompression;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.Unpooled;
import org.springframework.web.socket.BinaryMessage;

import java.nio.charset.StandardCharsets;
import java.util.zip.Inflater;

public class OkexBinaryMessageCompression implements BinaryMessageCompression {

    @Override
    public BinaryMessage compressToBinaryMessage(String s) {
        return null;
    }

    @Override
    public String decompressFromBinaryMessage(BinaryMessage binaryMessage) {

        byte[] bytes = binaryMessage.getPayload().array();
        ByteBuf byteBuf = Unpooled.wrappedBuffer(bytes);
        try {
            byte[] temp = new byte[byteBuf.readableBytes()];
            ByteBufInputStream bis = new ByteBufInputStream(byteBuf);
            bis.read(temp);
            bis.close();
            Inflater decompresser = new Inflater(true);
            decompresser.setInput(temp, 0, temp.length);
            StringBuilder sb = new StringBuilder();
            byte[] result = new byte[1024];
            while (!decompresser.finished()) {
                int resultLength = decompresser.inflate(result);
                sb.append(new String(result, 0, resultLength, StandardCharsets.UTF_8));
            }
            decompresser.end();
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
