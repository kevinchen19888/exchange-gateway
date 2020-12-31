package com.kevin.gateway.core.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.kevin.gateway.core.FiatCoin;

import java.io.IOException;

public class FiatCoinDeserializer extends JsonDeserializer<FiatCoin> {
    @Override
    public FiatCoin deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        try {
            return FiatCoin.of(p.getText());
        } catch (IllegalArgumentException e) {
            throw InvalidFormatException.from(p, "法币格式不正确", e);
        }

    }
}
