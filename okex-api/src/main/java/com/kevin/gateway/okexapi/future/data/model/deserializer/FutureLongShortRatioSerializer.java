package com.kevin.gateway.okexapi.future.data.model.deserializer;

import com.kevin.gateway.okexapi.future.data.model.FutureLongShortRatioResponse;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FutureLongShortRatioSerializer extends JsonDeserializer<FutureLongShortRatioResponse> {
    @Override
    public FutureLongShortRatioResponse deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {

        JsonNode jsonNode = jsonParser.readValueAs(JsonNode.class);
        FutureLongShortRatioResponse vo = new FutureLongShortRatioResponse();
        vo.setTimestamp(LocalDateTime.parse(jsonNode.get(0).asText(), DateTimeFormatter.ISO_DATE_TIME));
        vo.setLongShortRatio(new BigDecimal(jsonNode.get(1).asText()));
        return vo;

    }

}
