package com.kevin.gateway.okexapi.swap.websocket.response;

import com.kevin.gateway.okexapi.swap.vo.SwapTradeData;
import lombok.Data;
import lombok.Getter;

import java.util.List;

/** 示例
 * {
 *     "table": "swap/trade",
 *     "data": [{
 *         "instrument_id": "BTC-USD-SWAP",
 *         "price": "5611.9",
 *         "side": "buy",
 *         "size": "2",
 *         "timestamp": "2019-05-06T06:51:24.389Z",
 *         "trade_id": "227897880202387456"
 *     }]
 * }
 */
@Data
public class SwapWebsocketTradeResponse extends SwapWebsocketResponse {
    @Getter
    private List<SwapTradeData> data;

}
