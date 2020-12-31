package com.kevin.gateway.okexapi.index.serialize;

import com.kevin.gateway.okexapi.index.websocket.IndexChannel;
import com.fasterxml.jackson.databind.module.SimpleModule;

public final class IndexModule extends SimpleModule {
    public IndexModule() {
        super();

        addDeserializer(IndexChannel.class, new IndexChannelDeserializer());
    }
}
