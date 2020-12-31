package com.kevin.gateway.okexapi.future.data.model;

import com.kevin.gateway.okexapi.future.data.model.deserializer.FutureSentimentSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@JsonDeserialize(using = FutureSentimentSerializer.class)
public class FutureSentimentResponse {

    /**
     * 返回数据对应的时间
     */
    private LocalDateTime timestamp;


    /**
     * 做多账户比例
     */
    private BigDecimal longSentiment;


    /**
     * 做空账户比例
     */
    private BigDecimal shortSentiment;
}
