package com.kevin.gateway.okexapi.base.rest;

import com.kevin.gateway.okexapi.base.util.OkexEnvironment;
import io.github.bucket4j.Bucket;
import org.springframework.lang.Nullable;
import org.springframework.web.client.RestClientException;

public class PublicGetTemplateClient extends AbstractTemplateClient {

    protected PublicGetTemplateClient(OkexEnvironment environment, String apiPath, Bucket bucket) {
        super(environment, apiPath, bucket);
    }

    @Nullable
    public <T> T getForObject(Class<T> responseType, Object... uriVariables) throws RestClientException {
        rateLimit();

        String url = uriComponentsBuilder.buildAndExpand(uriVariables).toUriString();
        return environment.getRestTemplate().getForObject(url, responseType);
    }
}
