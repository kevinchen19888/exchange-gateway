package com.alchemy.gateway.exchangeclients.bitfinex.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Value;
import org.springframework.context.annotation.Primary;

import java.math.BigDecimal;

@Data
@JsonFormat(shape = JsonFormat.Shape.ARRAY)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BitfinexTradesResponse {
    private Long id;
    private String pair;
    private Long mtsCreate;
    private Long orderId;
    private BigDecimal execAmount;
    private BigDecimal execPrice;
    private  String placeHolder1;
    private  String placeHolder2;

    /**
     * 1: maker    -1 :taker;
     */
    private Integer maker;
    private BigDecimal fee;
    private String feeCoin;


    @JsonCreator
    public BitfinexTradesResponse(
            @JsonProperty("ID") Long id,
            @JsonProperty("pair") String pair,
            @JsonProperty("mtsCreate") Long mtsCreate,
            @JsonProperty("orderId") Long orderId,
            @JsonProperty("execAmount")BigDecimal execAmount,
            @JsonProperty("execPrice") BigDecimal execPrice,
            @JsonProperty("placeHolder1") String placeHolder1,
            @JsonProperty("placeHolder2") String placeHolder2,
            @JsonProperty("maker")Integer maker,
            @JsonProperty("fee") BigDecimal fee,
            @JsonProperty("feeCoin") String feeCoin)
    {
        this.id = id;
        this.pair = pair;
        this.mtsCreate = mtsCreate;
        this.orderId = orderId;
        this.execAmount = execAmount;
        this.execPrice = execPrice;
        this.placeHolder1 = placeHolder1;
        this.placeHolder2 = placeHolder2;
        this.maker = maker;
        this.fee = fee;
        this.feeCoin = feeCoin;
    }
}
