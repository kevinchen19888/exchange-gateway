package com.kevin.gateway.okexapi.future.websocket.response;


import com.kevin.gateway.okexapi.future.vo.FutureCandleData;
import lombok.Data;

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
public class FutureWebsocketCandleResponse extends FutureWebsocketResponse {
    private List<FutureCandleData> data;

}

