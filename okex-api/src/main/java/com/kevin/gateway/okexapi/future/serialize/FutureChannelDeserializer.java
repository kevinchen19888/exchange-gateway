package com.kevin.gateway.okexapi.future.serialize;

import com.kevin.gateway.okexapi.base.websocket.request.ChannelTrait;
import com.kevin.gateway.okexapi.future.websocket.FutureChannel;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * 对形如"swap/candle60s" 的swapchannel串信息反序列化为SwapChannel
 */
@Slf4j
public class FutureChannelDeserializer extends JsonDeserializer<FutureChannel> {
    @Override
    public FutureChannel deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        String data = jsonParser.getValueAsString();
        return FutureChannel.fromChannelName(ChannelTrait.fromSymbol(data).getChannelName());
    }
}

