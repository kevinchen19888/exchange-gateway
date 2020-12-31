package com.kevin.gateway.okexapi.index.serialize;


import com.kevin.gateway.okexapi.base.websocket.request.ChannelTrait;
import com.kevin.gateway.okexapi.index.websocket.IndexChannel;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * 对形如"index/candle60s" 的spotchannel串信息反序列化为IndexChannel
 */
@Slf4j
public class IndexChannelDeserializer extends JsonDeserializer<IndexChannel> {
    @Override
    public IndexChannel deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        String data = jsonParser.getValueAsString();
        return IndexChannel.fromChannelName(ChannelTrait.fromSymbol(data).getChannelName());
    }
}

