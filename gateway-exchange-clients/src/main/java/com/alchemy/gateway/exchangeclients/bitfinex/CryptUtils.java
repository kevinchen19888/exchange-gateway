package com.alchemy.gateway.exchangeclients.bitfinex;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * <ul>
 * <li>BASE64的加密解密是双向的，可以求反解。</li>
 * <li>MD5、SHA以及HMAC是单向加密，任何数据加密后只会产生唯一的一个加密串，通常用来校验数据在传输过程中是否被修改。</li>
 * <li>HMAC算法有一个密钥，增强了数据传输过程中的安全性，强化了算法外的不可控因素。</li>
 * <li>DES DES-Data Encryption Standard,即数据加密算法。
 * DES算法的入口参数有三个:Key、Data、Mode。
 * <ul>
 * <li>Key:8个字节共64位,是DES算法的工作密钥;</li>
 * <li>Data:8个字节64位,是要被加密或被解密的数据;</li>
 * <li>Mode:DES的工作方式,有两种:加密或解密。</li>
 * </ul>
 * </li>
 * <ul>
 *
 */
public class CryptUtils {


    public static String hmacEncode( String data,String key) throws Exception {

        byte[] sig = getHmacSha384(key).doFinal(data.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(sig);
    }

    public static Mac getHmacSha384(String secretKeyBase64) throws Exception {
       String  hmacString="HmacSHA384";
        final SecretKey secretKey = new SecretKeySpec(secretKeyBase64.getBytes(StandardCharsets.UTF_8), hmacString);
        Mac mac = Mac.getInstance(hmacString);
        mac.init(secretKey);
        return mac;
    }


    public static String bytesToHex(byte[] bytes) {
        char[] hexArray = "0123456789abcdef".toCharArray();
        char[] hexChars = new char[bytes.length * 2];

        for(int j = 0; j < bytes.length; ++j) {
            int v = bytes[j] & 255;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 15];
        }

        return new String(hexChars);
    }
}

