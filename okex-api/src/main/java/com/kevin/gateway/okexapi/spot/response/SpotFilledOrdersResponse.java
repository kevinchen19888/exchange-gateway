package com.kevin.gateway.okexapi.spot.response;

import com.kevin.gateway.okexapi.spot.vo.SpotTradeData;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 获取成交数据
 */
@Data
public class SpotFilledOrdersResponse extends SpotTradeData {
//    /**
//     * 成交时间
//     */
//    private LocalDateTime timestamp;
//
//    /**
//     * 成交ID
//     */
//    private String tradeId;
//
//    /**
//     * 成交价格
//     */
//    private BigDecimal price;
//
//    /**
//     * 成交数量
//     */
//    private BigDecimal size;
//
//    /**
//     * 成交方向
//     */
//    private OrderSide side;

    private LocalDateTime time;
}
