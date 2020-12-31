package com.kevin.gateway.core.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.kevin.gateway.core.CoinPair;

import java.io.IOException;

public class CoinPairDeserializer extends JsonDeserializer<CoinPair> {
    @Override
    public CoinPair deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        try {
            return CoinPair.of(p.getText());
        } catch (IllegalArgumentException e) {
            throw InvalidFormatException.from(p, "币对格式不正确", e);
        }
    }
}
