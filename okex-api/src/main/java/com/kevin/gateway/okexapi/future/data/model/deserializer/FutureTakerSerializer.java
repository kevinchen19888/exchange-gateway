package com.kevin.gateway.okexapi.future.data.model.deserializer;

import com.kevin.gateway.okexapi.future.data.model.FutureTakerResponse;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FutureTakerSerializer extends JsonDeserializer<FutureTakerResponse> {
    @Override
    public FutureTakerResponse deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {

        JsonNode jsonNode = jsonParser.readValueAs(JsonNode.class);
        FutureTakerResponse vo = new FutureTakerResponse();
        vo.setTimestamp(LocalDateTime.parse(jsonNode.get(0).asText(), DateTimeFormatter.ISO_DATE_TIME));
        vo.setBuyVolume(new BigDecimal(jsonNode.get(1).asText()));
        vo.setSellVolume(new BigDecimal(jsonNode.get(2).asText()));
        return vo;

    }

}