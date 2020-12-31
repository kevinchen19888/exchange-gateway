package com.kevin.gateway.okexapi.swap.websocket.response;


import com.kevin.gateway.okexapi.swap.vo.SwapInstrumentData;
import lombok.Data;

import java.util.List;

/**
 *{
 *     "table":"swap/instruments",
 *     "data":[{
 *         "instrument_id":"BTC-USD-SWAP",
 *         "underlying_index":"BTC",
 *         "quote_currency":"USD",
 *         "coin":"BTC",
 *         "contract_val":"100",
 *         "listing":"2018-08-28T02:43:23.000Z",
 *         "delivery":"2019-11-26T08:00:00.000Z",
 *         "size_increment":"1",
 *         "tick_size":"0.1",
 *         "base_currency":"BTC",
 *         "underlying":"BTC-USD",
 *         "settlement_currency":"BTC",
 *         "is_inverse":"true",
 *         "category":"1",
 *         "contract_val_currency":"USD"
 *     },
 *     {
 *         "instrument_id":"LTC-USD-SWAP",
 *         "underlying_index":"LTC",
 *         "quote_currency":"USD",
 *         "coin":"LTC",
 *         "contract_val":"10",
 *         "listing":"2018-12-21T07:53:47.000Z",
 *         "delivery":"2019-11-26T08:00:00.000Z",
 *         "size_increment":"1",
 *         "tick_size":"0.01",
 *         "base_currency":"LTC",
 *         "underlying":"LTC-USD",
 *         "settlement_currency":"LTC",
 *         "is_inverse":"true",
 *         "category":"1",
 *         "contract_val_currency":"USD"
 *     }
 *     ]
 * }
 */
@Data
public class SwapWebsocketInstrumentResponse extends SwapWebsocketResponse {
    private List<SwapInstrumentData> data;

}

