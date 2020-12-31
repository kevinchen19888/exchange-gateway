package com.kevin.gateway.okexapi.future.common.type;

import com.kevin.gateway.okexapi.future.common.model.HistoryKLineResponse;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FutureHistoryKLineVoSerializer extends JsonDeserializer<HistoryKLineResponse> {
    @Override
    public HistoryKLineResponse deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {

        JsonNode jsonNode = jsonParser.readValueAs(JsonNode.class);
        HistoryKLineResponse vo = new HistoryKLineResponse();
        vo.setTime(LocalDateTime.parse(jsonNode.get(0).asText(), DateTimeFormatter.ISO_DATE_TIME));
        vo.setOpen(new BigDecimal(jsonNode.get(1).asText()));
        vo.setHigh(new BigDecimal(jsonNode.get(2).asText()));
        vo.setLow(new BigDecimal(jsonNode.get(3).asText()));
        vo.setClose(new BigDecimal(jsonNode.get(4).asText()));
        vo.setVolume(new BigDecimal(jsonNode.get(5).asText()));

        return vo;

    }

}
