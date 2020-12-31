package com.kevin.gateway.okexapi.future.websocket.response;


import com.kevin.gateway.okexapi.future.vo.FutureMarkPriceData;
import lombok.Data;

import java.util.List;

/**
 * 交割合约的K线数据响应
 *  {
 *  "table": "future/mark_price",
 *  "data": [{
 *  "instrument_id": "LTC-USD-190628",
 *  "mark_price": "70.557",
 *  "timestamp": "2019-05-06T07:19:39.835Z"
 *  }]
 *  }
 */
@Data
public class FutureWebsocketMarkPriceResponse extends FutureWebsocketResponse {
    private List<FutureMarkPriceData> data;

}

