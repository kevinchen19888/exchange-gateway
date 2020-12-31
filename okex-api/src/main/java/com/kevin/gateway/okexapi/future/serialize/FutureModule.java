package com.kevin.gateway.okexapi.future.serialize;


import com.kevin.gateway.okexapi.future.websocket.FutureChannel;
import com.fasterxml.jackson.databind.module.SimpleModule;

public final class FutureModule extends SimpleModule {
    public FutureModule() {
        super();

        addDeserializer(FutureChannel.class, new FutureChannelDeserializer());
    }
}
