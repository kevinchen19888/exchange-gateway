package com.kevin.gateway.okexapi.spot.serialize;

import com.kevin.gateway.okexapi.spot.websocket.SpotChannel;
import com.fasterxml.jackson.databind.module.SimpleModule;

public final class SpotModule extends SimpleModule {
    public SpotModule() {
        super();

        addDeserializer(SpotChannel.class, new SpotChannelDeserializer());
    }
}
