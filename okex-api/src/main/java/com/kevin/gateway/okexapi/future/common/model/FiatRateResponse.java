package com.kevin.gateway.okexapi.future.common.model;


import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class FiatRateResponse {

    /**
     * USD_CNY  无法使用coinPair
     */
    private String instrumentId;

    /**
     * 汇率
     */
    private BigDecimal rate;


    /**
     * 时间
     */
    private LocalDateTime timestamp;


}
