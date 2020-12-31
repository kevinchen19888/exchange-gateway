package com.kevin.gateway.okexapi.future.vo;

import com.kevin.gateway.okexapi.future.FutureMarketId;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/** 示例
 {
 "table": "future/estimated_price",
 "data": [{
 "instrument_id": "BTC-USD-170310",
 "settlement_price": "22616.58",
 "timestamp": "2018-11-22T10:09:31.541Z"
 }]
 }
 */
@Data
public class FutureEstimatedPriceData {
    private FutureMarketId instrumentId;
    private BigDecimal settlementPrice;
    private LocalDateTime timestamp;

}
