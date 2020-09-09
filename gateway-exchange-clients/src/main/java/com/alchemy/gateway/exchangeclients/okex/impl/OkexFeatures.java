package com.alchemy.gateway.exchangeclients.okex.impl;

import com.alchemy.gateway.core.common.Credentials;
import com.alchemy.gateway.core.common.MarketType;
import com.alchemy.gateway.core.info.Features;
import com.alchemy.gateway.core.utils.DateUtils;
import com.alchemy.gateway.exchangeclients.okex.exception.APIException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class OkexFeatures implements Features {

    @Override
    public List<MarketType> supportMarketTypes() {
        return Arrays.asList(MarketType.SPOT, MarketType.FUTURE);
    }

    /**
     * 公共请求头
     *
     * @return HttpHeaders
     */
    public static HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("Cookie", "locale=zh_CN");
        headers.add("Content-Type", "application/json; charset=UTF-8");
        headers.add("User-Agent", "Postman");
        return headers;
    }

    /**
     * 需要验证的请求头
     *
     * @param credentials 用户信息
     * @param httpMethod  请求方式
     * @param requestPath 请求路径
     * @param queryString get请求参数
     * @param body        post,put请求实体
     * @return HttpHeaders
     */
    public static HttpHeaders getHeadersToApiKey(Credentials credentials, String httpMethod, String requestPath, String queryString, String body) {
        HttpHeaders headers = getHeaders();
        if (credentials != null) {
            String timestamp = DateUtils.getUtCTime(LocalDateTime.now(ZoneOffset.UTC));
            String sign = sign(credentials, httpMethod, requestPath, queryString, body, timestamp);
            headers.add("OK-ACCESS-KEY", credentials.getApiKey());
            headers.add("OK-ACCESS-SIGN", sign);
            headers.add("OK-ACCESS-PASSPHRASE", credentials.getPassphrase());
            headers.add("OK-ACCESS-TIMESTAMP", timestamp);
        }
        return headers;
    }

    /**
     * 签名
     *
     * @param credentials 用户信息
     * @param httpMethod  请求方式
     * @param requestPath 请求路径
     * @param queryString get请求参数
     * @param body        post,put请求实体
     * @param timestamp   时间戳
     * @return String
     */
    public static String sign(Credentials credentials, String httpMethod, String requestPath, String queryString, String body, String timestamp) {
        String sign;
        try {
            sign = HmacSHA256Base64Utils.sign(timestamp, httpMethod, requestPath, queryString, body, credentials.getSecretKey());
        } catch (final IOException e) {
            throw new APIException("Request get body io exception.", e);
        } catch (final CloneNotSupportedException e) {
            throw new APIException("Hmac SHA256 Base64 Signature clone not supported exception.", e);
        } catch (final InvalidKeyException e) {
            throw new APIException("Hmac SHA256 Base64 Signature invalid key exception.", e);
        }
        return sign;
    }

}
