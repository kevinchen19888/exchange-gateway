package com.kevin.gateway.okexapi.future.websocket.response;

import com.kevin.gateway.okexapi.future.vo.FutureTradeData;
import lombok.Data;
import lombok.Getter;

import java.util.List;

/** 示例
 * {
 *     "table": "future/trade",
 *     "data": [{
 *         "side": "buy",
 *         "trade_id": "2778148208082945",
 *         "price": "5556.91",
 *         "qty": "5",
 *         "instrument_id": "BTC-USD-190628",
 *         "timestamp": "2019-05-06T07:19:37.496Z"
 *     }]
 * }
 */
@Data
public class FutureWebsocketTradeResponse extends FutureWebsocketResponse {
    @Getter
    private List<FutureTradeData> data;


}
