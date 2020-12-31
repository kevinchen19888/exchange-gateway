package com.kevin.gateway.okexapi.swap.serialize;


import com.kevin.gateway.okexapi.swap.websocket.SwapChannel;
import com.fasterxml.jackson.databind.module.SimpleModule;

public final class SwapModule extends SimpleModule {
    public SwapModule() {
        super();

        addDeserializer(SwapChannel.class, new SwapChannelDeserializer());
    }
}
