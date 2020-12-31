package com.kevin.gateway.okexapi.base.rest;

import com.kevin.gateway.core.Credentials;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;

import java.time.Duration;
import java.util.Map;
import java.util.TreeMap;

public class CredentialsBucketContainer {
    private final Map<String, Bucket> bucketMap;
    private final long capacity;
    private final Duration duration;

    public CredentialsBucketContainer(long capacity, Duration duration) {
        bucketMap = new TreeMap<>();
        this.capacity = capacity;
        this.duration = duration;
    }

    public Bucket getBucketOrCreate(Credentials credentials) {
        if (!bucketMap.containsKey(credentials.getApiKey())) {
            Bandwidth limit = Bandwidth.simple(capacity, duration).withInitialTokens(1L);
            Bucket bucket = Bucket4j.builder().addLimit(limit).build();
            bucketMap.put(credentials.getApiKey(), bucket);
        }
        return bucketMap.get(credentials.getApiKey());
    }
}
