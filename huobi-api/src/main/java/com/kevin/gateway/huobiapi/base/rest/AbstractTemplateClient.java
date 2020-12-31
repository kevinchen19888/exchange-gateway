package com.kevin.gateway.huobiapi.base.rest;

import com.kevin.gateway.huobiapi.base.HuobiEnvironment;
import io.github.bucket4j.Bucket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.TreeMap;

@Slf4j
public abstract class AbstractTemplateClient {
    protected final HuobiEnvironment environment;
    protected final String apiPath;
    protected final UriComponentsBuilder uriComponentsBuilder;
    protected final Bucket bucket;
    protected final Map<String, Object> uriVariables;

    protected AbstractTemplateClient(HuobiEnvironment environment, String apiPath, Bucket bucket) {
        this.environment = environment;
        this.apiPath = apiPath;
        this.uriComponentsBuilder = UriComponentsBuilder.fromPath(apiPath);
        this.bucket = bucket;
        this.uriVariables = new TreeMap<>();
    }

    protected void rateLimit() {
        try {
            bucket.asScheduler().consume(1);
        } catch (InterruptedException e) {
            log.error("限速器发生异常", e);
            // 忽略此异常
        }
    }

    public UriComponentsBuilder getUriComponentsBuilder() {
        return uriComponentsBuilder;
    }
}
