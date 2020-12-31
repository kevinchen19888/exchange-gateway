package com.kevin.gateway.okexapi.index.vo;


import com.kevin.gateway.okexapi.index.IndexInstrumentId;
import lombok.Data;

@Data
public class IndexCandleData {
    /**
     * 		开始时间
     */
    private IndexInstrumentId instrumentId;
    /**
     *		K线详情
     */
    private IndexCandle candle;

}
