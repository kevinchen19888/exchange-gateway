package com.alchemy.gateway.exchangeclients.common;

import org.springframework.http.ResponseEntity;

/**
 * @author kevin chen
 */
public class HttpUtil {

    /**
     * get 请求
     */
    public static final String GET = "GET";
    /**
     * post 请求
     */
    public static final String POST = "POST";

    /**
     * http 请求header user-agent
     */
    public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36";

    public static <T> boolean isSuccessResp(ResponseEntity<T> entity) {
        return String.valueOf(entity.getStatusCode().value()).startsWith("2");
    }
}
