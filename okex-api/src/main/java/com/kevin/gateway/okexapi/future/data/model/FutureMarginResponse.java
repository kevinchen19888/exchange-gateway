package com.kevin.gateway.okexapi.future.data.model;

import com.kevin.gateway.okexapi.future.data.model.deserializer.FutureMarginSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@JsonDeserialize(using = FutureMarginSerializer.class)
public class FutureMarginResponse {

    /**
     * 返回数据对应的时间
     */
    private LocalDateTime timestamp;


    /**
     * 多头平均持仓比例
     */
    private BigDecimal longMarginRatio;


    /**
     * 空头平均持仓比例
     */
    private BigDecimal shortMarginRatio;
}
