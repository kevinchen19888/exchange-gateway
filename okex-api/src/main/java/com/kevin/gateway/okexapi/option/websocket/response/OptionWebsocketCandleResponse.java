package com.kevin.gateway.okexapi.option.websocket.response;


import com.kevin.gateway.okexapi.option.vo.OptionCandleData;
import lombok.Data;

import java.util.List;

/**
 * 期权的K线数据响应
 *  {
 *     "table": "option/candle60s",
 *     "data": [{
 *             "candle":[
 *                 "2019-09-06T15:32:00.000Z",
 *                 "11010.54",
 *                 "11015",
 *                 "11010.5",
 *                 "11014.01",
 *                 "4638",
 *                 "0.62"
 *             ],
 *             "instrument_id":"BTC-USD-190927-12500-C"
 *         }
 *     ]
 * }
 */
@Data
public class OptionWebsocketCandleResponse extends OptionWebsocketResponse {
    private List<OptionCandleData> data;

}

