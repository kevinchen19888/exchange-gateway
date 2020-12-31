package com.kevin.gateway.okexapi.swap.websocket.response;

import com.kevin.gateway.okexapi.swap.vo.SwapTickerData;
import lombok.Data;

import java.util.List;

/**
 * 合约公共-Ticker频道
 * <p>
 * 格式：{"table":"future/ticker",  "data":"[{"<value1>","<value2>"}]"}
 * <p>
 * 示例：
 * <pre>
 *  *  {
 *  *     "table":"swap/ticker",
 *  *     "data":[
 *  *         {
 *  *             "last":"170.91",
 *  *             "open_24h":"198.4",
 *  *             "best_bid":"170.92",
 *  *             "high_24h":"199.03",
 *  *             "low_24h":"166",
 *  *             "volume_24h":"31943233",
 *  *             "volume_token_24h":"1730040.0174",
 *  *             "best_ask":"170.97",
 *  *             "open_interest":"4162489",
 *  *             "instrument_id":"ETH-USD-SWAP",
 *  *             "timestamp":"2020-03-12T08:30:41.738Z",
 *  *             "best_bid_size":"101",
 *  *             "best_ask_size":"2050",
 *  *             "last_qty":"1"
 *  *         }
 *  *     ]
 *  *   }
 * </pre>
 */
@Data
public class SwapWebsocketTickerResponse extends SwapWebsocketResponse {

    private List<SwapTickerData> data;

}
