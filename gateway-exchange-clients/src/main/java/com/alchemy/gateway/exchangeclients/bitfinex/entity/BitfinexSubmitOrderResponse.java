package com.alchemy.gateway.exchangeclients.bitfinex.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Data
@NoArgsConstructor
public class BitfinexSubmitOrderResponse {

    private long mts;
    private String type;
    private String msgId;
    private String null1;

    private List<BitfinexSubmitOrderDetail> list;

    private String code;
    private String status;

    private String text;

    @JsonCreator
    public BitfinexSubmitOrderResponse(
            @JsonProperty("MTS") long mts,
            @JsonProperty("type") String type,
            @JsonProperty("msgId") String msgId,
            @JsonProperty("null1") String null1,
            @JsonProperty("list") List<BitfinexSubmitOrderDetail> list,
            @JsonProperty("code") String code,
            @JsonProperty("status") String status,
            @JsonProperty("text") String text) {
        this.mts = mts;
        this.type = type;
        this.msgId = msgId;
        this.null1 = null1;
        this.list = list;
        this.code = code;
        this.status = status;
        this.text = text;
    }
}
