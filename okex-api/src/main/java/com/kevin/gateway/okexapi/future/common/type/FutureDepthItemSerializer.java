package com.kevin.gateway.okexapi.future.common.type;

import com.kevin.gateway.okexapi.future.common.model.FutureDepthItem;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.math.BigDecimal;

public class FutureDepthItemSerializer extends JsonDeserializer<FutureDepthItem> {
    @Override
    public FutureDepthItem deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {

        JsonNode jsonNode = jsonParser.readValueAs(JsonNode.class);
        FutureDepthItem vo = new FutureDepthItem();
        vo.setPrice(new BigDecimal(jsonNode.get(0).asText()));
        vo.setSize(new BigDecimal(jsonNode.get(1).asText()));
        vo.setForceSize(jsonNode.get(2).asInt());
        vo.setOrderSize(jsonNode.get(3).asInt());

        return vo;

    }

}

