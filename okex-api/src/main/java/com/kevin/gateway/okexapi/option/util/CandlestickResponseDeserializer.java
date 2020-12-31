package com.kevin.gateway.okexapi.option.util;

import com.kevin.gateway.okexapi.option.domain.CandlestickResponse;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CandlestickResponseDeserializer extends JsonDeserializer<CandlestickResponse> {
    @Override
    public CandlestickResponse deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        try {
            JsonNode jsonNode = jsonParser.readValueAs(JsonNode.class);
            CandlestickResponse response = new CandlestickResponse();
            response.setTimestamp(LocalDateTime.parse(jsonNode.get(0).asText(), DateTimeFormatter.ISO_DATE_TIME));
            response.setOpen(new BigDecimal(jsonNode.get(1).asText()));
            response.setHigh(new BigDecimal(jsonNode.get(2).asText()));
            response.setLow(new BigDecimal(jsonNode.get(3).asText()));
            response.setClose(new BigDecimal(jsonNode.get(4).asText()));
            response.setVolume(Integer.parseInt(jsonNode.get(5).asText()));
            response.setCurrencyVolume(new BigDecimal(jsonNode.get(6).asText()));
            return response;
        } catch (Exception e) {
            throw InvalidFormatException.from(jsonParser, "K线数据格式不正确", e);
        }
    }

}
