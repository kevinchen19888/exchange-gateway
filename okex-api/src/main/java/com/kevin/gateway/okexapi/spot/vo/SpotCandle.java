package com.kevin.gateway.okexapi.spot.vo;


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

@Data
public class SpotCandle {
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
     *		交易量
     */
    private BigDecimal volume;

    @JsonCreator
    SpotCandle(@JsonProperty List<Object> data) throws IOException{
        if(data.size()<WebsocketConstants.SPOT_CANDLE_DATA_SIZE_MIN){
            throw new IOException("K线记录中数据个数不对："+data);
        }
        timestamp = LocalDateTime.ofInstant(Instant.parse((String)data.get(0)),ZoneOffset.UTC);
        open = new BigDecimal((String)data.get(1));
        high = new BigDecimal((String)data.get(2));
        low = new BigDecimal((String)data.get(3));
        close = new BigDecimal((String)data.get(4));
        volume = new BigDecimal((String)data.get(5));
    }

    public SpotCandle(){}
}
