package com.kevin.gateway.core.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.kevin.gateway.core.Coin;

import java.io.IOException;

public class CoinSerializer extends JsonSerializer<Coin> {
    @Override
    public void serialize(Coin value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.getSymbol());
    }
}
