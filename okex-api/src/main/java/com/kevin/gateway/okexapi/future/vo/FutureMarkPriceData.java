package com.kevin.gateway.okexapi.future.vo;

import com.kevin.gateway.okexapi.future.FutureMarketId;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/** 示例
 {
 "table": "future/mark_price",
 "data": [{
 "instrument_id": "LTC-USD-190628",
 "mark_price": "70.557",
 "timestamp": "2019-05-06T07:19:39.835Z"
 }]
 }
 */
@Data
public class FutureMarkPriceData {
    private FutureMarketId instrumentId;
    private BigDecimal markPrice;
    private LocalDateTime timestamp;

}
