package com.kevin.gateway.okexapi.future.common.model;


import com.kevin.gateway.okexapi.future.common.type.FutureHistoryKLineVoSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@JsonDeserialize(using = FutureHistoryKLineVoSerializer.class)
public class HistoryKLineResponse {

    /**
     * 开始时间
     */
    private LocalDateTime time;

    /**
     * 开盘价格
     */
    private BigDecimal open;

    /**
     * 最高价格
     */
    private BigDecimal high;

    /**
     * 最低价格
     */
    private BigDecimal low;

    /**
     * 收盘价格
     */
    private BigDecimal close;

    /**
     * 交易量（张）
     */
    private BigDecimal volume;


}

