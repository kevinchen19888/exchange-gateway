package com.kevin.gateway.okexapi.base.rest;

import com.kevin.gateway.okexapi.utils.HmacSHA256Base64Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * OKEX 客户端解释器，用于在 HTTP 头部添加认证信息
 */
@Slf4j
public class OkexCredentialsRequestInterceptor implements ClientHttpRequestInterceptor {

    private final boolean emulating;

    private static final String OKEX_USER_AGENT = System.getenv("OKEX_USER_AGENT");

    public OkexCredentialsRequestInterceptor(boolean emulating) {
        this.emulating = emulating;
    }

    // 此处由于在包级别声明了 @NonNullApi，但是此接口中没有声明，因此会产生警告，此处只能手工禁用次警告
    @SuppressWarnings("all")
    @Override
    public ClientHttpResponse intercept(HttpRequest request, @NonNull byte[] body, @NonNull ClientHttpRequestExecution execution) throws IOException {
        HttpHeaders headers = request.getHeaders();

        // 如果环境中设置了环境变量：OKEX_USER_AGENT
        // 则使用环境变量的值做为 User-Agent
        if (StringUtils.hasText(OKEX_USER_AGENT)) {
            headers.add(HttpHeaders.USER_AGENT, OKEX_USER_AGENT);
        }
        // 如果 Http 头中包含了 OK_CREDENTIALS 标志，则进行签名认证处理
        if (headers.containsKey(PrivateAbstractTemplateClient.OK_CREDENTIALS)) {
            // 处理特殊的 HTTP 头，参见：PrivateAbstractTemplateClient
            List<String> credentialsInfo = headers.getValuesAsList(PrivateAbstractTemplateClient.OK_CREDENTIALS);
            Assert.isTrue(credentialsInfo.size() == 3, PrivateAbstractTemplateClient.OK_CREDENTIALS + " 值长度不对！");

            headers.remove(PrivateAbstractTemplateClient.OK_CREDENTIALS);

            String apiKey = credentialsInfo.get(0);
            String secretKey = credentialsInfo.get(1);
            String passphrase = credentialsInfo.get(2);

            // Okex 需要一个没有毫秒的 UTC 时间
            // 例如：2020-09-18T12:17:57Z
            String timestamp = Instant.now().truncatedTo(ChronoUnit.SECONDS).toString();
            String accessSign = sign(secretKey, request.getMethodValue(), request.getURI().getPath(),
                    request.getURI().getQuery(), new String(body), timestamp);

            headers.add("OK-ACCESS-KEY", apiKey);
            headers.add("OK-ACCESS-SIGN", accessSign);
            headers.add("OK-ACCESS-TIMESTAMP", timestamp);
            headers.add("OK-ACCESS-PASSPHRASE", passphrase);
            if (emulating) {
                headers.add("x-simulated-trading", "1");
            }
        }

        return execution.execute(request, body);
    }

    /**
     * 签名
     *
     * @param secretKey   秘密键
     * @param httpMethod  请求方式
     * @param requestPath 请求路径
     * @param queryString get请求参数
     * @param body        post,put请求实体
     * @param timestamp   时间戳
     * @return String
     */
    private String sign(String secretKey, String httpMethod, String requestPath, String queryString, String body, String timestamp) {
        try {
            return HmacSHA256Base64Utils.sign(timestamp, httpMethod, requestPath, queryString, body, secretKey);
        } catch (InvalidKeyException | NoSuchAlgorithmException e) {
            log.error("签名错误", e);
            throw new IllegalStateException("签名错误！");
        }
    }

}
