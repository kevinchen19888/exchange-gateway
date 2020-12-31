package com.kevin.gateway.okexapi.margin.response;

import com.kevin.gateway.okexapi.margin.MarginMarketId;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 获取标记价格
 */
@Data
public class MarginMarkPriceResponse {

    /**
     * 杠杆币对名称
     */
    private MarginMarketId instrumentId;

    /**
     * 指定杠杆的标记价格
     */
    private BigDecimal markPrice;
    /**
     * 返回请求时间
     */
    private LocalDateTime timestamp;

}
