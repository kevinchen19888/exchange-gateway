package com.kevin.gateway.okexapi.option.websocket.response;

import com.kevin.gateway.okexapi.option.vo.OptionSummaryData;
import lombok.Data;

import java.util.List;


/** 期权详细定价
 * {
 *     "table":"option/summary",
 *     "data":[
 *         {
 *             "instrument_id":"BTC-USD-200110-9000-P",
 *             "underlying":"BTC-USD",
 *             "best_ask":"0.2495",
 *             "best_bid":"0.2395",
 *             "best_ask_size":"35",
 *             "best_bid_size":"35",
 *             "change_rate":"0",
 *             "delta":"-1.204251749",
 *             "gamma":"1.9206755384",
 *             "high_24h":"0",
 *             "highest_buy":"0.398",
 *             "realized_vol":"0",
 *             "bid_vol":"",
 *             "ask_vol":"0.957",
 *             "mark_vol":"0.7243",
 *             "last":"0",
 *             "leverage":"4.0922",
 *             "low_24h":"0",
 *             "lowest_sell":"0.0905",
 *             "mark_price":"0.013",
 *             "theta":"-0.0005075296",
 *             "vega":"0.0001420339",
 *             "volume_24h":"0",
 *             "open_interest":"0",
 *             "estimated_price":"0",
 *             "timestamp":"2019-12-31T08:13:28.794Z"
 *         }
 *     ]
 * }
 */
@Data
public class OptionWebsocketSummaryResponse extends OptionWebsocketResponse {
    private List<OptionSummaryData> data;

}

