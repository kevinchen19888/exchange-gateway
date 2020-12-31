package com.kevin.gateway.okexapi.fundingaccount.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class CurrencyVoDeserializer extends JsonDeserializer<Boolean> {
    @Override
    public Boolean deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String canDorW = jsonParser.getText();
        switch (canDorW) {
            case "0":
                return Boolean.FALSE;
            case "1":
                return Boolean.TRUE;
            default:
                throw new IllegalArgumentException("暂不支持的是否可充提条件转换,s:" + canDorW);
        }
    }
}
