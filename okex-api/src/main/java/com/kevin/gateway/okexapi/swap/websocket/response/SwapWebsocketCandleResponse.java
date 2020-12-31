package com.kevin.gateway.okexapi.swap.websocket.response;

import com.kevin.gateway.okexapi.swap.vo.SwapCandleData;
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
public class SwapWebsocketCandleResponse extends SwapWebsocketResponse {
    private List<SwapCandleData> data;

}

