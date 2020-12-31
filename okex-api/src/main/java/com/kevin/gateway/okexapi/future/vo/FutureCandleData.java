package com.kevin.gateway.okexapi.future.vo;


import com.kevin.gateway.okexapi.future.FutureMarketId;
import lombok.Data;

@Data
public class FutureCandleData {
    /**
     * 		开始时间
     */
    private FutureMarketId instrumentId;
    /**
     *		K线详情
     */
    private FutureCandle candle;

}
