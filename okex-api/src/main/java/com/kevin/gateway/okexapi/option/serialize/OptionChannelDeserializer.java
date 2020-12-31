package com.kevin.gateway.okexapi.option.serialize;


import com.kevin.gateway.okexapi.base.websocket.request.ChannelTrait;
import com.kevin.gateway.okexapi.option.websocket.OptionChannel;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * 对形如"option/candle60s" 的optionchannel串信息反序列化为OptionChannel
 */
@Slf4j
public class OptionChannelDeserializer extends JsonDeserializer<OptionChannel> {
    @Override
    public OptionChannel deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        String data = jsonParser.getValueAsString();
        return OptionChannel.fromChannelName(ChannelTrait.fromSymbol(data).getChannelName());
    }
}

