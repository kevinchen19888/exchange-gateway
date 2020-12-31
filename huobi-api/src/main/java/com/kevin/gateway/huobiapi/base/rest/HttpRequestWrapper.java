package com.kevin.gateway.huobiapi.base.rest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.net.URI;

public class HttpRequestWrapper implements HttpRequest {

    private final HttpRequest request;
    private final URI uri;

    public HttpRequestWrapper(HttpRequest request, URI uri) {
        Assert.notNull(request, "HttpRequest must not be null");
        this.request = request;
        this.uri = uri;
    }

    public HttpRequest getRequest() {
        return this.request;
    }

    @Nullable
    public HttpMethod getMethod() {
        return this.request.getMethod();
    }

    public String getMethodValue() {
        return this.request.getMethodValue();
    }

    public URI getURI() {
        return this.uri;
    }

    public HttpHeaders getHeaders() {
        return this.request.getHeaders();
    }
}