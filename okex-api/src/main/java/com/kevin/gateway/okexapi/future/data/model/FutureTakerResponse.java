package com.kevin.gateway.okexapi.future.data.model;

import com.kevin.gateway.okexapi.future.data.model.deserializer.FutureTakerSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@JsonDeserialize(using = FutureTakerSerializer.class)
public class FutureTakerResponse {

    /**
     * 返回数据对应的时间
     */
    private LocalDateTime timestamp;


    /**
     * 主动买入量
     */
    private BigDecimal buyVolume;


    /**
     * 主动卖出量
     */
    private BigDecimal sellVolume;

}
