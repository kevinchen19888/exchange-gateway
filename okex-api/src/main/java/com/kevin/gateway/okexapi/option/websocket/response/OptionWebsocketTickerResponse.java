package com.kevin.gateway.okexapi.option.websocket.response;

import com.kevin.gateway.okexapi.option.vo.OptionTickerData;
import lombok.Data;

import java.util.List;

/**
 * 合约公共-Ticker频道
 * <p>
 * 格式：{"table":"future/ticker",  "data":"[{"<value1>","<value2>"}]"}
 * <p>
 * 示例：
 * <pre>
 *  {
 *     "table":"option/ticker",
 *     "data":[
 *         {
 *             "last":"0.0015",
 *             "open_24h":"0.0035",
 *             "best_bid":"0.001",
 *             "high_24h":"0.0035",
 *             "low_24h":"0.0015",
 *             "volume_24h":"18",
 *             "volume_token_24h":"1.8",
 *             "best_ask":"0.002",
 *             "open_interest":"668",
 *             "instrument_id":"BTC-USD-200103-8000-C",
 *             "timestamp":"2019-12-31T07:59:05.519Z",
 *             "best_bid_size":"1",
 *             "best_ask_size":"305",
 *             "last_qty":"0"
 *         }
 *     ]
 * }
 * </pre>
 */
@Data
public class OptionWebsocketTickerResponse extends OptionWebsocketResponse {

    private List<OptionTickerData> data;

}
