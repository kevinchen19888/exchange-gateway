package com.kevin.gateway.okexapi.index.websocket.response;

import com.kevin.gateway.okexapi.index.vo.IndexCandleData;
import lombok.Data;

import java.util.List;

/**
 * 指数的K线数据
 *  * {
 *  *   "table":"index/candle60s",
 *  *   "data":[{
 *  *     "instrument_id":"BTC-USD",
 *  *     "candle":[
 *  *       "2018-11-27T10:01:23.341Z",
 *  *       "3811.31",
 *  *       "3811.31",
 *  *       "3811.31",
 *  *       "3811.31",
 *  *       "0"
 *  *     ]
 *  *   }]
 *  * }
 */
@Data
public class IndexWebsocketCandleResponse extends IndexWebsocketResponse {
    private List<IndexCandleData> data;

}

