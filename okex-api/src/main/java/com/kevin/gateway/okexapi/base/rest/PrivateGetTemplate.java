package com.kevin.gateway.okexapi.base.rest;

import com.kevin.gateway.core.Credentials;
import com.kevin.gateway.okexapi.base.util.OkexEnvironment;
import io.github.bucket4j.Bucket;
import lombok.Data;

import java.time.Duration;

@Data(staticConstructor = "of")
public final class PrivateGetTemplate {
    private final String path;
    private final CredentialsBucketContainer credentialsBucketContainer;

    private PrivateGetTemplate(String path, CredentialsBucketContainer credentialsBucketContainer) {
        this.path = path;
        this.credentialsBucketContainer = credentialsBucketContainer;
    }

    public static PrivateGetTemplate of(String path, long capacity, Duration duration) {
        return new PrivateGetTemplate(path, new CredentialsBucketContainer(capacity, duration));
    }

    public PrivateGetTemplateClient bind(OkexEnvironment environment, Credentials credentials) {
        Bucket bucket = credentialsBucketContainer.getBucketOrCreate(credentials);
        return new PrivateGetTemplateClient(environment, path, bucket, credentials);
    }
}
