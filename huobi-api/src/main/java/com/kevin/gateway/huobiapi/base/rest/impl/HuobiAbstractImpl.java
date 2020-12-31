package com.kevin.gateway.huobiapi.base.rest.impl;

import com.kevin.gateway.huobiapi.base.HuobiEnvironment;

public abstract class HuobiAbstractImpl {

    protected final HuobiEnvironment environment;

    public HuobiAbstractImpl(HuobiEnvironment environment) {
        this.environment = environment;
    }
}
