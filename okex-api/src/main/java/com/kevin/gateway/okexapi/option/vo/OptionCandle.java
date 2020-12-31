package com.kevin.gateway.okexapi.option.vo;


import com.kevin.gateway.okexapi.base.websocket.WebsocketConstants;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

/**
 * 交割合约的K线数据响应
 * {
 "table":"future/candle180s",
 "data":[
 {
 "candle":[
 "2019-09-25T10:00:00.000Z",
 "8533.02",
 "8553.74",
 "8527.17",
 "8548.26",
 "45247",
 "529.5858061"
 ],
 "instrument_id":"BTC-USD-191227"
 }
 ]
 * }
 */
@Data
public class OptionCandle {
    /**
     * 		开始时间
     */
    private LocalDateTime timestamp;
    /**
     *		开盘价格
     */
    private BigDecimal open;
    /**
     *		最高价格
     */
    private BigDecimal high;
    /**
     *		最低价格
     */
    private BigDecimal low;
    /**
     *		收盘价格
     */
    private BigDecimal close;
    /**
     *		交易量(按张折算)
     */
    private int volume;
    /**
     *		交易量(按币种折算)
     */
    private BigDecimal currencyVolume;

    @JsonCreator
    OptionCandle(@JsonProperty List<Object> data) throws IOException{
        if(data.size()<WebsocketConstants.OPTION_CANDLE_DATA_SIZE_MIN){
            throw new IOException("K线记录中数据个数不对："+data);
        }
        timestamp = LocalDateTime.ofInstant(Instant.parse((String)data.get(0)),ZoneOffset.UTC);
        open = new BigDecimal((String)data.get(1));
        high = new BigDecimal((String)data.get(2));
        low = new BigDecimal((String)data.get(3));
        close = new BigDecimal((String)data.get(4));
        volume = Integer.parseInt((String)data.get(5));
        currencyVolume = new BigDecimal((String)data.get(6));
    }

    public OptionCandle(){}
}
