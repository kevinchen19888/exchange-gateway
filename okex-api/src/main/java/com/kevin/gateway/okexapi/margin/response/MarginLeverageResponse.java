package com.kevin.gateway.okexapi.margin.response;

import com.kevin.gateway.okexapi.margin.MarginMarketId;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 获取杠杆倍数
 */
@Data
public class MarginLeverageResponse {
    /**
     * 币对名称，如：BTC-USDT
     */
    private MarginMarketId instrumentId;
    /**
     * 杠杆倍数
     */
    private BigDecimal leverage;
    /**
     * 错误码
     */
    private String errorCode;
    /**
     * 错误信息
     */
    private String errorMessage;
    /**
     * 请求结果
     */
    private String result;
}
