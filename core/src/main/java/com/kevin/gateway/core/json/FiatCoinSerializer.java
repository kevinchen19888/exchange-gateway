package com.kevin.gateway.core.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.kevin.gateway.core.FiatCoin;

import java.io.IOException;

public class FiatCoinSerializer extends JsonSerializer<FiatCoin> {
    @Override
    public void serialize(FiatCoin value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.getSymbol());
    }
}
