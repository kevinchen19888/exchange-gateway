package com.kevin.gateway.okexapi.base.rest.impl;

import com.kevin.gateway.okexapi.base.util.OkexEnvironment;

public abstract class OkexAbstractImpl {

    protected final OkexEnvironment environment;

    public OkexAbstractImpl(OkexEnvironment environment) {
        this.environment = environment;
    }
}
