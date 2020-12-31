package com.kevin.gateway.okexapi.base.rest;

import com.kevin.gateway.core.Credentials;
import com.kevin.gateway.okexapi.base.util.OkexEnvironment;
import io.github.bucket4j.Bucket;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.client.RestClientException;

public class PrivateGetTemplateClient extends PrivateAbstractTemplateClient {

    public PrivateGetTemplateClient(OkexEnvironment environment, String apiPath, Bucket bucket, Credentials credentials) {
        super(environment, apiPath, bucket, credentials);
    }

    @Nullable
    public <T> T getForObject(Class<T> responseType, Object... uriVariables) throws RestClientException {
        rateLimit();

        String url = uriComponentsBuilder.buildAndExpand(uriVariables).toUriString();
        HttpEntity<String> httpEntity = createCredentialsHttpEntity(credentials);
        ResponseEntity<T> responseEntity = environment.getRestTemplate().exchange(url, HttpMethod.GET, httpEntity, responseType);
        return responseEntity.getBody();
    }
}
