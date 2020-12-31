package com.kevin.gateway.okexapi.option.serialize;

import com.kevin.gateway.okexapi.option.websocket.OptionChannel;
import com.fasterxml.jackson.databind.module.SimpleModule;

public final class OptionModule extends SimpleModule {
    public OptionModule() {
        super();

        addDeserializer(OptionChannel.class, new OptionChannelDeserializer());
    }
}
