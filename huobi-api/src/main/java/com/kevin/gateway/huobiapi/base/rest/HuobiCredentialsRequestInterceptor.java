package com.kevin.gateway.huobiapi.base.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;
import org.springframework.web.util.UriComponentsBuilder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;

/**
 * huobi 客户端解释器，用于在 HTTP 头部添加认证信息
 */
@Slf4j
public class HuobiCredentialsRequestInterceptor implements ClientHttpRequestInterceptor {

    private final boolean emulating;

    private static final String HUOBI_USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36";
    //private static final String HUOBI_USER_AGENT = System.getenv("HUOBI_USER_AGENT");

    public HuobiCredentialsRequestInterceptor(boolean emulating) {
        this.emulating = emulating;
    }

    // 此处由于在包级别声明了 @NonNullApi，但是此接口中没有声明，因此会产生警告，此处只能手工禁用次警告
    @SuppressWarnings("all")
    @Override
    public ClientHttpResponse intercept(HttpRequest request, @NonNull byte[] body, @NonNull ClientHttpRequestExecution execution) throws IOException {
        HttpHeaders headers = request.getHeaders();
        headers.add(HttpHeaders.USER_AGENT, HUOBI_USER_AGENT);

        // 如果环境中设置了环境变量：HUOBI_USER_AGENT
        // 则使用环境变量的值做为 User-Agent
//        if (StringUtils.hasText(HUOBI_USER_AGENT)) {
//            headers.add(HttpHeaders.USER_AGENT, HUOBI_USER_AGENT);
//        }

        URI uri = null;

        // 如果 Http 头中包含了 OK_CREDENTIALS 标志，则进行签名认证处理
        if (headers.containsKey(PrivateAbstractTemplateClient.HUOBI_CREDENTIALS)) {
            // 处理特殊的 HTTP 头，参见：PrivateAbstractTemplateClient
            List<String> credentialsInfo = headers.getValuesAsList(PrivateAbstractTemplateClient.HUOBI_CREDENTIALS);
            Assert.isTrue(credentialsInfo.size() == 2, PrivateAbstractTemplateClient.HUOBI_CREDENTIALS + " 值长度不对！");

            headers.remove(PrivateAbstractTemplateClient.HUOBI_CREDENTIALS);
            String apiKey = credentialsInfo.get(0);
            String secretKey = credentialsInfo.get(1);

            //处理请求参数
            UrlParamsBuilder urlParamsBuilder = UrlParamsBuilder.build();
            if (request.getURI().getQuery() != null && !request.getURI().getQuery().equals(""))
                urlParamsBuilder.putToUrl(request.getURI().getQuery());
            //签名
            String accessSign = createSignature(apiKey, secretKey, request.getMethodValue(), request.getURI().getHost(),
                    request.getURI().getPath(), urlParamsBuilder).buildUrl();
            //获取路径
            String url = request.getURI().toString().split("\\?")[0];
            //合成请求完整路径
            uri = UriComponentsBuilder.fromUriString(url + accessSign).build(true).toUri();
        }

        return execution.execute(uri == null ? request : new HttpRequestWrapper(request, uri), body);
    }

    private static final String ACCESS_KEY_ID = "AccessKeyId";
    private static final String SIGNATURE_METHOD = "SignatureMethod";
    private static final String SIGNATURE_METHOD_VALUE = "HmacSHA256";
    private static final String SIGNATURE_VERSION = "SignatureVersion";
    public static final String SIGNATURE_VERSION_VALUE = "2";
    private static final String TIMESTAMP = "Timestamp";
    private static final String SIGNATURE = "Signature";

    private static final DateTimeFormatter DT_FORMAT = DateTimeFormatter
            .ofPattern("uuuu-MM-dd'T'HH:mm:ss");
    private static final ZoneId ZONE_GMT = ZoneId.of("Z");

    private static UrlParamsBuilder createSignature(String accessKey, String secretKey, String method, String host,
                                                    String uri, UrlParamsBuilder builder) {
        StringBuilder sb = new StringBuilder(1024);

        if (accessKey == null || "".equals(accessKey) || secretKey == null || "".equals(secretKey)) {
            throw new HuobiApiException(HuobiApiException.KEY_MISSING,
                    "API key and secret key are required");
        }

        sb.append(method.toUpperCase()).append('\n')
                .append(host.toLowerCase()).append('\n')
                .append(uri).append('\n');

        builder.putToUrl(ACCESS_KEY_ID, accessKey)
                .putToUrl(SIGNATURE_VERSION, SIGNATURE_VERSION_VALUE)
                .putToUrl(SIGNATURE_METHOD, SIGNATURE_METHOD_VALUE)
                .putToUrl(TIMESTAMP, gmtNow());

        sb.append(builder.buildSignature());
        Mac hmacSha256;
        try {
            hmacSha256 = Mac.getInstance(SIGNATURE_METHOD_VALUE);
            SecretKeySpec secKey = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8),
                    SIGNATURE_METHOD_VALUE);
            hmacSha256.init(secKey);
        } catch (NoSuchAlgorithmException e) {
            throw new HuobiApiException(HuobiApiException.RUNTIME_ERROR,
                    "[Signature] No such algorithm: " + e.getMessage());
        } catch (InvalidKeyException e) {
            throw new HuobiApiException(HuobiApiException.RUNTIME_ERROR,
                    "[Signature] Invalid key: " + e.getMessage());
        }
        String payload = sb.toString();
        byte[] hash = hmacSha256.doFinal(payload.getBytes(StandardCharsets.UTF_8));

        String actualSign = Base64.getEncoder().encodeToString(hash);

        return builder.putToUrl(SIGNATURE, actualSign);
    }

    private static long epochNow() {
        return Instant.now().getEpochSecond();
    }

    static String gmtNow() {
        return Instant.ofEpochSecond(epochNow()).atZone(ZONE_GMT).format(DT_FORMAT);
    }

}
