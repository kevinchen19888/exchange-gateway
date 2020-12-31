package com.kevin.gateway.okexapi.spot.websocket.response;

import com.kevin.gateway.okexapi.base.websocket.request.ChannelTrait;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;


@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "table", visible = true)
@JsonSubTypes({@JsonSubTypes.Type(value = SpotWebsocketDepth5Response.class, name = "spot/depth5")
        , @JsonSubTypes.Type(value = SpotWebsocketDepthResponse.class, name = "spot/depth")
        , @JsonSubTypes.Type(value = SpotWebsocketDepthL2TbtResponse.class, name = "spot/depth_l2_tbt")
        , @JsonSubTypes.Type(value = SpotWebsocketTickerResponse.class, name = "spot/ticker")
        , @JsonSubTypes.Type(value = SpotWebsocketTradeResponse.class, name = "spot/trade")
        , @JsonSubTypes.Type(value = SpotWebsocketCandleResponse.class, name = "spot/candle60s")
        , @JsonSubTypes.Type(value = SpotWebsocketCandleResponse.class, name = "spot/candle180s")
        , @JsonSubTypes.Type(value = SpotWebsocketCandleResponse.class, name = "spot/candle300s")
        , @JsonSubTypes.Type(value = SpotWebsocketCandleResponse.class, name = "spot/candle900s")
        , @JsonSubTypes.Type(value = SpotWebsocketCandleResponse.class, name = "spot/candle1800s")
        , @JsonSubTypes.Type(value = SpotWebsocketCandleResponse.class, name = "spot/candle3600s")
        , @JsonSubTypes.Type(value = SpotWebsocketCandleResponse.class, name = "spot/candle7200s")
        , @JsonSubTypes.Type(value = SpotWebsocketCandleResponse.class, name = "spot/candle14400s")
        , @JsonSubTypes.Type(value = SpotWebsocketCandleResponse.class, name = "spot/candle21600s")
        , @JsonSubTypes.Type(value = SpotWebsocketCandleResponse.class, name = "spot/candle43200s")
        , @JsonSubTypes.Type(value = SpotWebsocketCandleResponse.class, name = "spot/candle86400s")
        , @JsonSubTypes.Type(value = SpotWebsocketCandleResponse.class, name = "spot/candle604800s")})
public abstract class SpotWebsocketResponse {
    @JsonProperty("table")
    public ChannelTrait channel;

}
