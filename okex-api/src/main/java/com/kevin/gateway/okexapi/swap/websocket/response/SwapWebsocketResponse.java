package com.kevin.gateway.okexapi.swap.websocket.response;


import com.kevin.gateway.okexapi.base.websocket.request.ChannelTrait;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;


@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "table", visible = true)
@JsonSubTypes({@JsonSubTypes.Type(value = SwapWebsocketFundingRateResponse.class, name = "swap/funding_rate")
        ,@JsonSubTypes.Type(value = SwapWebsocketDepth5Response.class, name = "swap/depth5")
        , @JsonSubTypes.Type(value = SwapWebsocketDepth400Response.class, name = "swap/depth")
        , @JsonSubTypes.Type(value = SwapWebsocketDepthL2TbtResponse.class, name = "swap/depth_l2_tbt")
        , @JsonSubTypes.Type(value = SwapWebsocketTickerResponse.class, name = "swap/ticker")
        , @JsonSubTypes.Type(value = SwapWebsocketTradeResponse.class, name = "swap/trade")
        , @JsonSubTypes.Type(value = SwapWebsocketMarkPriceResponse.class, name = "swap/mark_price")
        , @JsonSubTypes.Type(value = SwapWebsocketPriceRangeResponse.class, name = "swap/price_range")
        , @JsonSubTypes.Type(value = SwapWebsocketCandleResponse.class, name = "swap/candle60s")
        , @JsonSubTypes.Type(value = SwapWebsocketCandleResponse.class, name = "swap/candle180s")
        , @JsonSubTypes.Type(value = SwapWebsocketCandleResponse.class, name = "swap/candle300s")
        , @JsonSubTypes.Type(value = SwapWebsocketCandleResponse.class, name = "swap/candle900s")
        , @JsonSubTypes.Type(value = SwapWebsocketCandleResponse.class, name = "swap/candle1800s")
        , @JsonSubTypes.Type(value = SwapWebsocketCandleResponse.class, name = "swap/candle3600s")
        , @JsonSubTypes.Type(value = SwapWebsocketCandleResponse.class, name = "swap/candle7200s")
        , @JsonSubTypes.Type(value = SwapWebsocketCandleResponse.class, name = "swap/candle14400s")
        , @JsonSubTypes.Type(value = SwapWebsocketCandleResponse.class, name = "swap/candle21600s")
        , @JsonSubTypes.Type(value = SwapWebsocketCandleResponse.class, name = "swap/candle43200s")
        , @JsonSubTypes.Type(value = SwapWebsocketCandleResponse.class, name = "swap/candle86400s")
        , @JsonSubTypes.Type(value = SwapWebsocketCandleResponse.class, name = "swap/candle604800s")})
public abstract class SwapWebsocketResponse {
    @JsonProperty("table")
    public ChannelTrait channel;

}
