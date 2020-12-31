package com.kevin.gateway.okexapi.swap.websocket.response;

import com.kevin.gateway.okexapi.swap.vo.SwapFundingRateData;
import lombok.Data;

import java.util.List;

/** 合约资金费率
 * {
 *     "table":"swap/funding_rate",
 *     "data":[
 *         {
 *             "estimated_rate":"0.00019",
 *             "funding_rate":"0.00022993",
 *             "funding_time":"2019-10-11T16:00:00.000Z",
 *             "instrument_id":"BTC-USD-SWAP",
 *             "interest_rate":"0",
 *             "settlement_time":"2019-10-12T08:00:00.000Z"
 *         }
 *     ]
 * }
 */
@Data
public class SwapWebsocketFundingRateResponse extends SwapWebsocketResponse {
    private List<SwapFundingRateData> data;
}

