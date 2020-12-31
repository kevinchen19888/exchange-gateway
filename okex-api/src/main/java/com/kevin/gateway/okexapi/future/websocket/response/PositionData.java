package com.kevin.gateway.okexapi.future.websocket.response;

import com.kevin.gateway.okexapi.future.FutureMarketId;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY,property = "margin_mode")
@JsonSubTypes(value = {
        @JsonSubTypes.Type(value = PositionFixedData.class, name = "fixed"),
        @JsonSubTypes.Type(value = PositionCrossedData.class, name = "crossed")
})
@Data
public class PositionData {
    /**
     * 合约ID，如BTC-USDT-180909,BTC-USDT-191227
     */
    private FutureMarketId instrumentId;

}