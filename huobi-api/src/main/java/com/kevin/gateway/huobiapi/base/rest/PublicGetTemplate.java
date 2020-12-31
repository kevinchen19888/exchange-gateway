package com.kevin.gateway.huobiapi.base.rest;

import com.kevin.gateway.huobiapi.base.HuobiEnvironment;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import lombok.Data;

import java.time.Duration;

@Data(staticConstructor = "of")
public final class PublicGetTemplate {

    private final String path;
    private final Bucket bucket;

    private PublicGetTemplate(String path, Bucket bucket) {
        this.path = path;
        this.bucket = bucket;
    }

    public static PublicGetTemplate of(String path, long capacity, Duration duration) {
        Bandwidth limit = Bandwidth.simple(capacity, duration).withInitialTokens(1L);
        Bucket bucket = Bucket4j.builder().addLimit(limit).build();

        return new PublicGetTemplate(path, bucket);
    }

    public PublicGetTemplateClient bind(HuobiEnvironment environment) {
        return new PublicGetTemplateClient(environment, path, bucket);
    }
}
