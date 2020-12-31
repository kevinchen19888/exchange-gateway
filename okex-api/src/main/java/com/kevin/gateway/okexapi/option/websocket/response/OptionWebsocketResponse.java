package com.kevin.gateway.okexapi.option.websocket.response;


import com.kevin.gateway.okexapi.base.websocket.request.ChannelTrait;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;



@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "table", visible = true)
@JsonSubTypes({@JsonSubTypes.Type(value = OptionWebsocketInstrumentResponse.class, name = "option/instruments")
        ,@JsonSubTypes.Type(value = OptionWebsocketSummaryResponse.class, name = "option/summary")
        ,@JsonSubTypes.Type(value = OptionWebsocketDepth5Response.class, name = "option/depth5")
        , @JsonSubTypes.Type(value = OptionWebsocketDepth400Response.class, name = "option/depth")
        , @JsonSubTypes.Type(value = OptionWebsocketDepthL2TbtResponse.class, name = "option/depth_l2_tbt")
        , @JsonSubTypes.Type(value = OptionWebsocketTickerResponse.class, name = "option/ticker")
        , @JsonSubTypes.Type(value = OptionWebsocketTradeResponse.class, name = "option/trade")
        , @JsonSubTypes.Type(value = OptionWebsocketCandleResponse.class, name = "option/candle60s")
        , @JsonSubTypes.Type(value = OptionWebsocketCandleResponse.class, name = "option/candle180s")
        , @JsonSubTypes.Type(value = OptionWebsocketCandleResponse.class, name = "option/candle300s")
        , @JsonSubTypes.Type(value = OptionWebsocketCandleResponse.class, name = "option/candle900s")
        , @JsonSubTypes.Type(value = OptionWebsocketCandleResponse.class, name = "option/candle1800s")
        , @JsonSubTypes.Type(value = OptionWebsocketCandleResponse.class, name = "option/candle3600s")
        , @JsonSubTypes.Type(value = OptionWebsocketCandleResponse.class, name = "option/candle7200s")
        , @JsonSubTypes.Type(value = OptionWebsocketCandleResponse.class, name = "option/candle14400s")
        , @JsonSubTypes.Type(value = OptionWebsocketCandleResponse.class, name = "option/candle21600s")
        , @JsonSubTypes.Type(value = OptionWebsocketCandleResponse.class, name = "option/candle43200s")
        , @JsonSubTypes.Type(value = OptionWebsocketCandleResponse.class, name = "option/candle86400s")
        , @JsonSubTypes.Type(value = OptionWebsocketCandleResponse.class, name = "option/candle604800s")})
public abstract class OptionWebsocketResponse {
    @JsonProperty("table")
    public ChannelTrait channel;

}
