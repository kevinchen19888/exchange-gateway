package com.alchemy.gateway.broker.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class OrderRecordMessage {
    @JsonProperty("order_id")
    private Long orderId;//订单id
    @JsonProperty("order_record_id")
    private String orderRecordId;//成交记录id
    @JsonProperty("exchange_deal_id")
    private String exchangeDealId;//交易所成交名称
    @JsonProperty("amount")
    private String amount;//成交数量
    @JsonProperty("price")
    private String price;//成交价格
    @JsonProperty("fee")
    private String fee;//费用
    @JsonProperty("fee_coin")
    private String feeCoin;//费用币种
    @JsonProperty("deal_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime dealTime;//成交时间
}
