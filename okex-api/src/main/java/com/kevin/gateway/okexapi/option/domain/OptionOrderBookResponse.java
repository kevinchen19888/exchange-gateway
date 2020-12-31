package com.kevin.gateway.okexapi.option.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 期权合约的深度数据响应
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class OptionOrderBookResponse extends OptionErrorResponse {
    /**
     * 卖方深度
     */
    private List<List<BigDecimal>> asks;
    /**
     * 买方深度
     */
    private List<List<BigDecimal>> bids;
    /**
     * 系统时间戳，ISO 8061格式
     */
    private LocalDateTime timestamp;
}
