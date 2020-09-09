package com.alchemy.gateway.exchangeclients.huobi.websocket;

import com.alchemy.gateway.core.websocket.BinaryMessageCompression;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.BinaryMessage;

import java.io.*;
import java.util.zip.GZIPInputStream;

/**
 * huobi 接收的推送数据解压缩
 *
 * @author kevin
 */
@Slf4j
public class HuobiBinaryMessageCompression implements BinaryMessageCompression {

    @Override
    public BinaryMessage compressToBinaryMessage(String s) {
        // 暂时不需要
        return null;
    }

    @Override
    public String decompressFromBinaryMessage(BinaryMessage binaryMessage) {
        byte[] bytes = binaryMessage.getPayload().array();
        try {
            return new String(decode(bytes));
        } catch (Exception e) {
            log.error("huobi解压缩推送数据异常:{}", e.toString());
        }
        return "";
    }

    public static byte[] decode(byte[] data) throws Exception {
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        decompress(bais, baos);
        baos.flush();
        baos.close();
        bais.close();
        return baos.toByteArray();
    }

    private static void decompress(InputStream is, OutputStream os) throws IOException {
        GZIPInputStream gis = new GZIPInputStream(is);
        int count;
        byte[] data = new byte[1024];
        while ((count = gis.read(data, 0, 1024)) != -1) {
            os.write(data, 0, count);
        }
        gis.close();
    }
}
