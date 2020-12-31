package com.kevin.gateway.okexapi.margin.request;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 设置杠杆倍数
 */
@Data
public class MarginLeverageRequest {
    private BigDecimal leverage;
}
