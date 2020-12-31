package com.kevin.gateway.okexapi.future.data.model;


import com.kevin.gateway.okexapi.future.data.model.deserializer.FutureLongShortRatioSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@JsonDeserialize(using = FutureLongShortRatioSerializer.class)
public class FutureLongShortRatioResponse {

    /**
     * 	返回数据对应的时间
     */
   private LocalDateTime timestamp;


    /**
     * 	多空人数比
     */
   private BigDecimal longShortRatio;


}
