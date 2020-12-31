package com.kevin.gateway.okexapi.option.websocket.response;


import com.kevin.gateway.okexapi.option.vo.OptionTradeData;
import lombok.Data;
import java.util.List;

/** 示例
 * {
 *     "table": "option/trade",
 *     "data": [{
 *             "instrument_id": "BTC-USD-190927-12500-C",
 *             "trade_id": "227897880202387456",
 *             "price": "0.017",
 *             "side": "buy",
 *             "qty": "2",
 *             "timestamp": "2019-05-06T06:51:24.389Z"
 *         },
 *         {
 *             "instrument_id": "BTC-USD-190927-12500-C",
 *             "trade_id": "227897880202387457",
 *             "price": "0.019",
 *             "side": "buy",
 *             "qty": "1",
 *             "timestamp": "2019-05-06T06:51:24.392Z"
 *         }
 *     ]
 * }
 */
@Data
public class OptionWebsocketTradeResponse extends OptionWebsocketResponse {
    private List<OptionTradeData> data;

}
