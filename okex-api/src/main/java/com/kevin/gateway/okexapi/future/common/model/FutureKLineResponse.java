package com.kevin.gateway.okexapi.future.common.model;


import com.kevin.gateway.okexapi.future.common.type.FutureKLineSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@JsonDeserialize(using = FutureKLineSerializer.class)
public class FutureKLineResponse {

    /**
     * 开始时间
     */
    private LocalDateTime timestamp;

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
    private int volume;

    /**
     * 按币种折算的交易量
     */
    private BigDecimal currencyVolume;

}

