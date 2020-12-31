package com.kevin.gateway.okexapi.swap.common.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.math.BigDecimal;

public class SwapDepthItemSerializer extends JsonDeserializer<SwapDepthItemResponse> {
    @Override
    public SwapDepthItemResponse deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {

        JsonNode jsonNode = jsonParser.readValueAs(JsonNode.class);
        SwapDepthItemResponse vo = new SwapDepthItemResponse();
        vo.setPrice(new BigDecimal(jsonNode.get(0).asText()));
        vo.setSize(new BigDecimal(jsonNode.get(1).asText()));
        vo.setForceSize(jsonNode.get(2).asInt());
        vo.setOrderSize(jsonNode.get(3).asInt());

        return vo;

    }

}

