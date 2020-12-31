package com.kevin.gateway.okexapi.swap.websocket.response;

import com.kevin.gateway.okexapi.swap.vo.SwapMarkPriceData;
import lombok.Data;

import java.util.List;

/** 示例
 * {
 *     "table": "swap/mark_price",
 *     "data": [{
 *         "instrument_id": "BTC-USD-SWAP",
 *         "mark_price": "5620.9",
 *         "timestamp": "2019-05-06T07:03:33.799Z"
 *     }]
 * }
 */
@Data
public class SwapWebsocketMarkPriceResponse extends SwapWebsocketResponse {
    private List<SwapMarkPriceData> data;

}

