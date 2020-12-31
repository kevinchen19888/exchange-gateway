package com.kevin.gateway.okexapi.future.websocket.response;

import com.kevin.gateway.okexapi.base.websocket.request.ChannelTrait;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;


@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "table", visible = true)
@JsonSubTypes({@JsonSubTypes.Type(value = FutureWebsocketInstrumentResponse.class, name = "futures/instruments")
        ,@JsonSubTypes.Type(value = FutureWebsocketEstimatedPriceResponse.class, name = "futures/estimated_price")
        ,@JsonSubTypes.Type(value = FutureWebsocketDepth5Response.class, name = "futures/depth5")
        , @JsonSubTypes.Type(value = FutureWebsocketDepth400Response.class, name = "futures/depth")
        , @JsonSubTypes.Type(value = FutureWebsocketDepthL2TbtResponse.class, name = "futures/depth_l2_tbt")
        , @JsonSubTypes.Type(value = FutureWebsocketTickerResponse.class, name = "futures/ticker")
        , @JsonSubTypes.Type(value = FutureWebsocketTradeResponse.class, name = "futures/trade")
        , @JsonSubTypes.Type(value = FutureWebsocketMarkPriceResponse.class, name = "futures/mark_price")
        , @JsonSubTypes.Type(value = FutureWebsocketPriceRangeResponse.class, name = "futures/price_range")
        , @JsonSubTypes.Type(value = FutureWebsocketCandleResponse.class, name = "futures/candle60s")
        , @JsonSubTypes.Type(value = FutureWebsocketCandleResponse.class, name = "futures/candle180s")
        , @JsonSubTypes.Type(value = FutureWebsocketCandleResponse.class, name = "futures/candle300s")
        , @JsonSubTypes.Type(value = FutureWebsocketCandleResponse.class, name = "futures/candle900s")
        , @JsonSubTypes.Type(value = FutureWebsocketCandleResponse.class, name = "futures/candle1800s")
        , @JsonSubTypes.Type(value = FutureWebsocketCandleResponse.class, name = "futures/candle3600s")
        , @JsonSubTypes.Type(value = FutureWebsocketCandleResponse.class, name = "futures/candle7200s")
        , @JsonSubTypes.Type(value = FutureWebsocketCandleResponse.class, name = "futures/candle14400s")
        , @JsonSubTypes.Type(value = FutureWebsocketCandleResponse.class, name = "futures/candle21600s")
        , @JsonSubTypes.Type(value = FutureWebsocketCandleResponse.class, name = "futures/candle43200s")
        , @JsonSubTypes.Type(value = FutureWebsocketCandleResponse.class, name = "futures/candle86400s")
        , @JsonSubTypes.Type(value = FutureWebsocketCandleResponse.class, name = "futures/candle604800s")})
public abstract class FutureWebsocketResponse {
    @JsonProperty("table")
    public ChannelTrait channel;

}
