package com.kevin.gateway.okexapi.base.rest;

import com.kevin.gateway.core.Credentials;
import com.kevin.gateway.okexapi.base.util.OkexEnvironment;
import io.github.bucket4j.Bucket;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.client.RestClientException;

public class PrivatePostTemplateClient extends PrivateAbstractTemplateClient {

    public PrivatePostTemplateClient(OkexEnvironment environment, String apiPath, Bucket bucket, Credentials credentials) {
        super(environment, apiPath, bucket, credentials);
    }

    @Nullable
    public <T> T postForObject(@Nullable Object request, Class<T> responseType,
                               Object... uriVariables) throws RestClientException {
        rateLimit();

        String url = uriComponentsBuilder.buildAndExpand(uriVariables).toUriString();
        HttpEntity<?> httpEntity = createCredentialsHttpEntity(credentials, request);
        ResponseEntity<T> responseEntity = environment.getRestTemplate().exchange(url, HttpMethod.POST, httpEntity, responseType);
        return responseEntity.getBody();

    }
}
