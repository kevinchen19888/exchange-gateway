package com.kevin.gateway.okexapi.swap.websocket.response;

import com.kevin.gateway.okexapi.swap.vo.SwapPriceRangeData;
import lombok.Data;

import java.util.List;

/**
 * 交割合约的最高买价和最低卖价
 *  {
 *     "table": "swap/price_range",
 *     "data": [{
 *         "highest": "5665.9",
 *         "instrument_id": "BTC-USD-SWAP",
 *         "lowest": "5553.6",
 *         "timestamp": "2019-05-06T06:51:20.012Z"
 *     }]
 * }
 */
@Data
public class SwapWebsocketPriceRangeResponse extends SwapWebsocketResponse {
    private List<SwapPriceRangeData> data;

}

