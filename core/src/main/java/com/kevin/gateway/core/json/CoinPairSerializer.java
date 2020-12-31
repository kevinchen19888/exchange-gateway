package com.kevin.gateway.core.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.kevin.gateway.core.CoinPair;

import java.io.IOException;

public class CoinPairSerializer extends JsonSerializer<CoinPair> {
    @Override
    public void serialize(CoinPair value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.getSymbol());
    }
}
