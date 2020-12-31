package com.kevin.gateway.okexapi.future.websocket.response;


import com.kevin.gateway.okexapi.future.vo.FutureEstimatedPriceData;
import lombok.Data;

import java.util.List;

/**
 * 交割合约的K线数据响应
 *  {
 *  "table": "future/estimated_price",
 *  "data": [{
 *  "instrument_id": "BTC-USD-170310",
 *  "settlement_price": "22616.58",
 *  "timestamp": "2018-11-22T10:09:31.541Z"
 *  }]
 *  }
 */
@Data
public class FutureWebsocketEstimatedPriceResponse extends FutureWebsocketResponse {
    private List<FutureEstimatedPriceData> data;

}

