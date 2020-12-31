package com.kevin.gateway.okexapi.future.websocket.response;


import com.kevin.gateway.okexapi.future.vo.FuturePriceRangeData;
import lombok.Data;

import java.util.List;

/**
 * 交割合约的最高买价和最低卖价
 *  {
 *  *     "table": "future/price_range",
 *  *     "data": [{
 *  *         "highest": "5729.32",
 *  *         "lowest": "5392.32",
 *  *         "instrument_id": "BTC-USD-190628",
 *  *         "timestamp": "2019-05-06T07:19:35.004Z"
 *  *     }]
 *  * }
 */
@Data
public class FutureWebsocketPriceRangeResponse extends FutureWebsocketResponse {
    private List<FuturePriceRangeData> data;

}

