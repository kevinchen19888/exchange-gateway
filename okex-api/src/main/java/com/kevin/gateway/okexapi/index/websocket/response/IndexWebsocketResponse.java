package com.kevin.gateway.okexapi.index.websocket.response;

import com.kevin.gateway.okexapi.base.websocket.request.ChannelTrait;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;


@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "table", visible = true)
@JsonSubTypes({ @JsonSubTypes.Type(value = IndexWebsocketTickerResponse.class, name = "index/ticker")
        , @JsonSubTypes.Type(value = IndexWebsocketCandleResponse.class, name = "index/candle60s")
        , @JsonSubTypes.Type(value = IndexWebsocketCandleResponse.class, name = "index/candle180s")
        , @JsonSubTypes.Type(value = IndexWebsocketCandleResponse.class, name = "index/candle300s")
        , @JsonSubTypes.Type(value = IndexWebsocketCandleResponse.class, name = "index/candle900s")
        , @JsonSubTypes.Type(value = IndexWebsocketCandleResponse.class, name = "index/candle1800s")
        , @JsonSubTypes.Type(value = IndexWebsocketCandleResponse.class, name = "index/candle3600s")
        , @JsonSubTypes.Type(value = IndexWebsocketCandleResponse.class, name = "index/candle7200s")
        , @JsonSubTypes.Type(value = IndexWebsocketCandleResponse.class, name = "index/candle14400s")
        , @JsonSubTypes.Type(value = IndexWebsocketCandleResponse.class, name = "index/candle21600s")
        , @JsonSubTypes.Type(value = IndexWebsocketCandleResponse.class, name = "index/candle43200s")
        , @JsonSubTypes.Type(value = IndexWebsocketCandleResponse.class, name = "index/candle86400s")
        , @JsonSubTypes.Type(value = IndexWebsocketCandleResponse.class, name = "index/candle604800s")})
public abstract class IndexWebsocketResponse {
    @JsonProperty("table")
    public ChannelTrait channel;

}
