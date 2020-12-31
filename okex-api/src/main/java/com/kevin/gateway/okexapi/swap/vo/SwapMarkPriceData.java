package com.kevin.gateway.okexapi.swap.vo;

import com.kevin.gateway.okexapi.swap.SwapMarketId;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/** 示例
 * {
 *     "table": "swap/mark_price",
 *     "data": [{
 *         "instrument_id": "BTC-USD-SWAP",
 *         "mark_price": "5620.9",
 *         "timestamp": "2019-05-06T07:03:33.799Z"
 *     }]
 * }
 */
@Data
public class SwapMarkPriceData {
    private SwapMarketId instrumentId;
    private BigDecimal markPrice;
    private LocalDateTime timestamp;

}
