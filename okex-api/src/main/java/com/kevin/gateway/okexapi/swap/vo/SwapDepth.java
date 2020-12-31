package com.kevin.gateway.okexapi.swap.vo;

import com.kevin.gateway.okexapi.base.websocket.WebsocketConstants;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 *  {
 *     "table": "swap/depth5",
 *     "data": [{
 *         "asks": [
 *             ["5621.7", "58", "0", "2"],
 *             ["5621.8", "125", "0", "5"],
 *             ["5622", "84", "0", "2"],
 *             ["5622.5", "6", "0", "1"],
 *             ["5623", "1", "0", "1"]
 *         ],
 *         "bids": [
 *             ["5621.3", "287", "0", "8"],
 *             ["5621.2", "41", "0", "1"],
 *             ["5621.1", "2", "0", "1"],
 *             ["5621", "26", "0", "2"],
 *             ["5620.9", "640", "0", "1"]
 *         ],
 *         "instrument_id": "BTC-USD-SWAP",
 *         "timestamp": "2019-05-06T07:03:33.048Z"
 *     }]
 * }
 */

@Data
public class SwapDepth {
    private BigDecimal price;

    /**
     * 该价位的合约张数
     */
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
    SwapDepth(@JsonProperty List<Object> data) throws IOException{
        if(data.size()<WebsocketConstants.DEPTH_DATA_SIZE_MIN){
            throw new IOException("深度数据记录中数据个数不对："+data);
        }
        price = new BigDecimal((String)data.get(0));
        volume = Integer.parseInt((String)data.get(1));
        stopOut = Integer.parseInt((String)data.get(2));
        orders = Integer.parseInt((String)data.get(3));
    }
}
