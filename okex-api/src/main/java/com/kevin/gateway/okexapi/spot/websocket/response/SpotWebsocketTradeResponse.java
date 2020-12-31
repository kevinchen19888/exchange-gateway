package com.kevin.gateway.okexapi.spot.websocket.response;


import com.kevin.gateway.okexapi.spot.vo.SpotTradeData;
import lombok.Data;
import lombok.Getter;

import java.util.List;

/** 示例
 * {
 *     "table": "spot/trade",
 *     "data": [{
 *         "instrument_id": "ETH-USDT",
 *         "price": "162.12",
 *         "side": "buy",
 *         "size": "11.085",
 *         "timestamp": "2019-05-06T06:51:24.389Z",
 *         "trade_id": "1210447366"
 *     }]
 * }
 */
@Data
public class SpotWebsocketTradeResponse extends SpotWebsocketResponse {
    @Getter
    private List<SpotTradeData> data;
}
