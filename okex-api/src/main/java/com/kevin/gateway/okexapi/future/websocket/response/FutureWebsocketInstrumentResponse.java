package com.kevin.gateway.okexapi.future.websocket.response;


import com.kevin.gateway.okexapi.future.vo.FutureInstrumentData;
import lombok.Data;

import java.util.List;

/**
 *{
 *     "table":"future/instruments",
 *     "data":[
 *         [
 *             {
 *                 "instrument_id":"BTC-USD-191115",
 *                 "underlying_index":"BTC",
 *                 "quote_currency":"USD",
 *                 "tick_size":"0.01",
 *                 "contract_val":"100",
 *                 "listing":"2019-11-01",
 *                 "delivery":"2019-11-15",
 *                 "trade_increment":"1",
 *                 "alias":"next_week",
 *                 "underlying":"BTC-USD",
 *                 "base_currency":"BTC",
 *                 "settlement_currency":"BTC",
 *                 "is_inverse":"true",
 *                 "contract_val_currency":"USD"
 *             },
 *             {
 *                 "instrument_id":"TRX-USD-191115",
 *                 "underlying_index":"TRX",
 *                 "quote_currency":"USD",
 *                 "tick_size":"0.00001",
 *                 "contract_val":"10",
 *                 "listing":"2019-11-01",
 *                 "delivery":"2019-11-15",
 *                 "trade_increment":"1",
 *                 "alias":"next_week",
 *                 "underlying":"TRX-USD",
 *                 "base_currency":"TRX",
 *                 "settlement_currency":"TRX",
 *                 "is_inverse":"true",
 *                 "contract_val_currency":"USD"
 *             }
 *         ]
 *     ]
 * }
 */
@Data
public class FutureWebsocketInstrumentResponse extends FutureWebsocketResponse {
    private List<FutureInstrumentData> data;

}

