package com.kevin.gateway.okexapi.future.data.model;

import com.kevin.gateway.okexapi.future.data.model.deserializer.FutureVolumeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@JsonDeserialize(using = FutureVolumeSerializer.class)
public class FutureVolumeResponse {

    /**
     * 返回数据对应的时间
     */
    private LocalDateTime timestamp;


    /**
     * 持仓总量
     */
    private BigDecimal openInsterest;


    /**
     * 交易总量
     */
    private BigDecimal volume;


}
