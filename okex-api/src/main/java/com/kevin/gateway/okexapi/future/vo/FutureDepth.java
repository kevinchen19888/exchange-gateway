package com.kevin.gateway.okexapi.future.vo;

import com.kevin.gateway.okexapi.base.websocket.WebsocketConstants;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Data
public class FutureDepth {
    private BigDecimal price;

    private int volume;

    /**
     * 该价位的委托单数
     */
    private int orders;

    /**
     * 该价位强制平仓的委托单数
     */
    private int stopOut;
    @JsonCreator
    FutureDepth(@JsonProperty List<Object> data) throws IOException{
        if(data.size()<WebsocketConstants.DEPTH5_DATA_SIZE_MIN){
            throw new IOException("深度数据记录中数据个数不对："+data);
        }
        price = new BigDecimal((String)data.get(0));
        volume = Integer.parseInt((String)data.get(1));
        if(data.size()==WebsocketConstants.DEPTH5_DATA_SIZE_MIN){
            orders = Integer.parseInt((String) data.get(2));
        }else {
            stopOut = Integer.parseInt((String) data.get(2));
            orders = Integer.parseInt((String) data.get(3));
        }
    }
}
