package com.kevin.gateway.huobiapi.base.rest;

import com.kevin.gateway.core.Credentials;
import com.kevin.gateway.huobiapi.base.HuobiEnvironment;
import io.github.bucket4j.Bucket;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Arrays;

public abstract class PrivateAbstractTemplateClient extends AbstractTemplateClient {
    public static final String HUOBI_CREDENTIALS = "HUOBI-CREDENTIALS";

    protected final Credentials credentials;

    public PrivateAbstractTemplateClient(HuobiEnvironment environment, String apiPath, Bucket bucket, Credentials credentials) {
        super(environment, apiPath, bucket);
        this.credentials = credentials;
    }

    protected HttpEntity<String> createCredentialsHttpEntity(Credentials credentials) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // 此处添加一个特殊的 HTTP Header，在 OkexCredentialsRequestInterceptor 会处理此 HTTP 头
        // 参见：OkexCredentialsRequestInterceptor
        headers.addAll(HUOBI_CREDENTIALS, Arrays.asList(credentials.getApiKey(), credentials.getSecretKey()));
        return new HttpEntity<>(headers);
    }

    protected <T> HttpEntity<T> createCredentialsHttpEntity(Credentials credentials, T request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // 此处添加一个特殊的 HTTP Header，在 OkexCredentialsRequestInterceptor 会处理此 HTTP 头
        // 参见：OkexCredentialsRequestInterceptor
        headers.addAll(HUOBI_CREDENTIALS, Arrays.asList(credentials.getApiKey(), credentials.getSecretKey()));
        return new HttpEntity<>(request, headers);
    }


}
