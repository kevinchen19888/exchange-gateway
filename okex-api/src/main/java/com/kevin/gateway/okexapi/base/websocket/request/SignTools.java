package com.kevin.gateway.okexapi.base.websocket.request;

import com.kevin.gateway.core.Credentials;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class SignTools {
    public static String sign(Credentials credentials, long timestamp) {
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(credentials.getSecretKey().getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            String content = timestamp + "GET" + "/users/self/verify";
            return Base64.encodeBase64String(sha256_HMAC.doFinal(content.getBytes()));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new IllegalStateException("创建 HmacSHA256 时出错", e);
        }

    }
}
