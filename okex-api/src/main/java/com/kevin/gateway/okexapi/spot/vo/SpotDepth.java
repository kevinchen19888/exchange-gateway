package com.kevin.gateway.okexapi.spot.vo;

import com.kevin.gateway.okexapi.base.websocket.WebsocketConstants;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Data
public class SpotDepth {
    private BigDecimal price;
    private BigDecimal volume;

    /**
     * 此价格的订单个数
     */
    private int orders;

    /**
     * 此价格的现货杠杆强平数量
     */
    private int stepOut;

    public SpotDepth() {
    }
    @JsonCreator
    SpotDepth(@JsonProperty List<Object> data) throws IOException {
        if(data.size()<WebsocketConstants.DEPTH5_DATA_SIZE_MIN){
            throw new IOException("深度数据记录中数据个数不对："+data);
        }
        price = new BigDecimal((String)data.get(0));
        volume = new BigDecimal((String)data.get(1));
        if(data.size()==WebsocketConstants.DEPTH5_DATA_SIZE_MIN){
            orders = Integer.parseInt((String) data.get(2));
        }else {
            stepOut = Integer.parseInt((String) data.get(2));
            orders = Integer.parseInt((String) data.get(3));
        }
    }
}
