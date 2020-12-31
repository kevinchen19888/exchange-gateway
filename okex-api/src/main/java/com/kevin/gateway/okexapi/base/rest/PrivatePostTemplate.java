package com.kevin.gateway.okexapi.base.rest;

import com.kevin.gateway.core.Credentials;
import io.github.bucket4j.Bucket;
import lombok.Data;
import com.kevin.gateway.okexapi.base.util.OkexEnvironment;

import java.time.Duration;

@Data(staticConstructor = "of")
public final class PrivatePostTemplate {
    private final String path;
    private final CredentialsBucketContainer credentialsBucketContainer;

    private PrivatePostTemplate(String path, CredentialsBucketContainer credentialsBucketContainer) {
        this.path = path;
        this.credentialsBucketContainer = credentialsBucketContainer;
    }

    public static PrivatePostTemplate of(String path, long capacity, Duration duration) {
        return new PrivatePostTemplate(path, new CredentialsBucketContainer(capacity, duration));
    }

    public PrivatePostTemplateClient bind(OkexEnvironment environment, Credentials credentials) {
        Bucket bucket = credentialsBucketContainer.getBucketOrCreate(credentials);
        return new PrivatePostTemplateClient(environment, path, bucket, credentials);
    }

}
