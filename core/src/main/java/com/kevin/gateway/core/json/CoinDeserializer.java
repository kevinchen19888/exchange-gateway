package com.kevin.gateway.core.json;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.kevin.gateway.core.Coin;

import java.io.IOException;

public class CoinDeserializer extends JsonDeserializer<Coin> {
    @Override
    public Coin deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        try {
            return Coin.of(p.getText());
        } catch (IllegalArgumentException e) {
            throw InvalidFormatException.from(p, "币种格式不正确", e);
        }
    }
}
