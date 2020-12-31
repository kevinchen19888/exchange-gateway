package com.kevin.gateway.okexapi.swap.vo;

import com.kevin.gateway.okexapi.swap.SwapMarketId;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/** 示例
 * {
 *     "table": "swap/price_range",
 *     "data": [{
 *         "highest": "5665.9",
 *         "instrument_id": "BTC-USD-SWAP",
 *         "lowest": "5553.6",
 *         "timestamp": "2019-05-06T06:51:20.012Z"
 *     }]
 * }
 */
@Data
public class SwapPriceRangeData {
    private SwapMarketId instrumentId;
    private BigDecimal highest;
    private BigDecimal lowest;
    private LocalDateTime timestamp;

}
