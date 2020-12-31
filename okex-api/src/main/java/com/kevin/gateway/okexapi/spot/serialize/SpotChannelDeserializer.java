package com.kevin.gateway.okexapi.spot.serialize;


import com.kevin.gateway.okexapi.base.websocket.request.ChannelTrait;
import com.kevin.gateway.okexapi.spot.websocket.SpotChannel;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * 对形如"spot/candle60s" 的spotchannel串信息反序列化为SpotChannel
 */
@Slf4j
public class SpotChannelDeserializer extends JsonDeserializer<SpotChannel> {
    @Override
    public SpotChannel deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        String data = jsonParser.getValueAsString();
        return SpotChannel.fromChannelName(ChannelTrait.fromSymbol(data).getChannelName());
    }
}

