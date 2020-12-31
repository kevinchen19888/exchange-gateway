package com.kevin.gateway.okexapi.future.vo;

import com.kevin.gateway.okexapi.future.FutureMarketId;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/** 示例
 *{
 *     "table": "future/price_range",
 *     "data": [{
 *         "highest": "5729.32",
 *         "lowest": "5392.32",
 *         "instrument_id": "BTC-USD-190628",
 *         "timestamp": "2019-05-06T07:19:35.004Z"
 *     }]
 * }
 */
@Data
public class FuturePriceRangeData {
    private FutureMarketId instrumentId;
    private BigDecimal highest;
    private BigDecimal lowest;
    private LocalDateTime timestamp;

}
