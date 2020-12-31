package com.kevin.gateway.okexapi.future.websocket.response;

import com.kevin.gateway.okexapi.future.vo.FutureTickerData;
import lombok.Data;

import java.util.List;

/**
 * 合约公共-Ticker频道
 * <p>
 * 格式：{"table":"future/ticker",  "data":"[{"<value1>","<value2>"}]"}
 * <p>
 * 示例：
 * <pre>
 * {
 *     "table":"future/ticker",
 *     "data":[
 *         {
 *             "last":"43.259",
 *             "open_24h":"49.375",
 *             "best_bid":"43.282",
 *             "high_24h":"49.488",
 *             "low_24h":"41.649",
 *             "volume_24h":"11295421",
 *             "volume_token_24h":"2430793.6742",
 *             "best_ask":"43.317",
 *             "open_interest":"1726003",
 *             "instrument_id":"LTC-USD-200327",
 *             "timestamp":"2020-03-12T08:31:45.288Z",
 *             "best_bid_size":"171",
 *             "best_ask_size":"2",
 *             "last_qty":"1"
 *         }
 *     ]
 * }
 * </pre>
 */
@Data
public class FutureWebsocketTickerResponse extends FutureWebsocketResponse {

    private List<FutureTickerData> data;

}
