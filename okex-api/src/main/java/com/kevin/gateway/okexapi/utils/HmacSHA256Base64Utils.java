package com.kevin.gateway.okexapi.utils;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Hmac SHA256 Base64 Signature Utils.<br/>
 */
public class HmacSHA256Base64Utils {

    /**
     * Signing a Message.<br/>
     * <p>
     * using: Hmac SHA256 + base64
     *
     * @param timestamp   the number of seconds since Unix Epoch in UTC. Decimal values are allowed.
     *                    eg: 2018-03-08T10:59:25.789Z
     * @param method      eg: POST
     * @param requestPath eg: /orders
     * @param queryString eg: before=2&limit=30
     * @param body        json string, eg: {"product_id":"BTC-USD-0309","order_id":"377454671037440"}
     * @param secretKey   user's secret key eg: E65791902180E9EF4510DB6A77F6EBAE
     * @return signed string   eg: TO6uwdqz+31SIPkd4I+9NiZGmVH74dXi+Fd5X0EzzSQ=
     */
    public static String sign(String timestamp, String method, String requestPath,
                              String queryString, String body, String secretKey)
            throws InvalidKeyException, NoSuchAlgorithmException {
        Assert.hasText(secretKey, "安全密钥（secretKey）不能为空");
        Assert.hasText(method, "HTTP 谓词(GET、POST)不能为空");

        String preHash = preHash(timestamp, method, requestPath, queryString, body);

        Mac mac = Mac.getInstance("HmacSHA256");
        byte[] secretKeyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKeyBytes, "HmacSHA256");
        mac.init(secretKeySpec);
        return Base64.getEncoder().encodeToString(mac.doFinal(preHash.getBytes(StandardCharsets.UTF_8)));
    }

    /**
     * the prehash string = timestamp + method + requestPath + body .<br/>
     *
     * @param timestamp   the number of seconds since Unix Epoch in UTC. Decimal values are allowed.
     *                    eg: 2018-03-08T10:59:25.789Z
     * @param method      eg: POST
     * @param requestPath eg: /orders
     * @param queryString eg: before=2&limit=30
     * @param body        json string, eg: {"product_id":"BTC-USD-0309","order_id":"377454671037440"}
     * @return prehash string eg: 2018-03-08T10:59:25.789ZPOST/orders?before=2&limit=30{"product_id":"BTC-USD-0309",
     * "order_id":"377454671037440"}
     */
    public static String preHash(String timestamp, String method, String requestPath,
                                 String queryString, String body) {
        StringBuilder preHash = new StringBuilder();
        preHash.append(timestamp);
        preHash.append(method.toUpperCase());
        preHash.append(requestPath);
        //get方法
        if (!StringUtils.isEmpty(queryString)) {
            //在queryString前面拼接上？
            preHash.append("?").append(queryString);
        }
        //post方法
        if (!StringUtils.isEmpty(body)) {
            preHash.append(body);
        }
        return preHash.toString();
    }

}