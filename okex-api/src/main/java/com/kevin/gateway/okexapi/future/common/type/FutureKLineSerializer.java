package com.kevin.gateway.okexapi.future.common.type;

import com.kevin.gateway.okexapi.future.common.model.FutureKLineResponse;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FutureKLineSerializer extends JsonDeserializer<FutureKLineResponse> {
    @Override
    public FutureKLineResponse deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {

        JsonNode jsonNode = jsonParser.readValueAs(JsonNode.class);
        FutureKLineResponse vo = new FutureKLineResponse();
        vo.setTimestamp(LocalDateTime.parse(jsonNode.get(0).asText(), DateTimeFormatter.ISO_DATE_TIME));
        vo.setOpen(new BigDecimal(jsonNode.get(1).asText()));
        vo.setHigh(new BigDecimal(jsonNode.get(2).asText()));
        vo.setLow(new BigDecimal(jsonNode.get(3).asText()));
        vo.setClose(new BigDecimal(jsonNode.get(4).asText()));
        vo.setVolume(Integer.parseInt(jsonNode.get(5).asText()));
        vo.setCurrencyVolume(new BigDecimal(jsonNode.get(6).asText()));
        return vo;

    }

}
