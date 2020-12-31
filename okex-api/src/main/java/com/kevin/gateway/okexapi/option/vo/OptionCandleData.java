package com.kevin.gateway.okexapi.option.vo;


import com.kevin.gateway.okexapi.option.OptionMarketId;
import lombok.Data;

@Data
public class OptionCandleData {
    /**
     * 		开始时间
     */
    private OptionMarketId instrumentId;
    /**
     *		K线详情
     */
    private OptionCandle candle;

}
