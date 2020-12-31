package com.kevin.gateway.okexapi.option.websocket.response;

import com.kevin.gateway.okexapi.option.vo.OptionInstrumentData;
import lombok.Data;

import java.util.List;

/**
 * {
 *     "table": "option/instruments",
 *     "data": [{
 *             "instrument_id": "BTC-USD-190927-8500-P",
 *             "underlying": "BTC-USD",
 *             "settlement_currency": "BTC",
 *             "contract_val": "0.1000",
 *             "option_type": "P",
 *             "strike": "8500",
 *             "tick_size": "8",
 *             "lot_size": "1.0000",
 *             "listing": "2019-08-28T08:00:00.000Z",
 *             "delivery": "2019-09-27T08:00:00.000Z",
 *             "state": "2",
 *             "trading_start_time": "2019-08-28T07:30:39.678Z"
 *         },
 *         {
 *             "instrument_id": "BTC-USD-190927-8500-C",
 *             "underlying": "BTC-USD",
 *             "settlement_currency": "BTC",
 *             "contract_val": "0.1000",
 *             "option_type": "C",
 *             "strike": "8500",
 *             "tick_size": "8",
 *             "lot_size": "1.0000",
 *             "listing": "2019-08-28T08:00:00.000Z",
 *             "delivery": "2019-09-27T08:00:00.000Z",
 *             "state": "2",
 *             "trading_start_time": "2019-08-28T07:30:39.678Z"
 *         }
 *     ]
 * }
 */
@Data
public class OptionWebsocketInstrumentResponse extends OptionWebsocketResponse {
    private List<OptionInstrumentData> data;

}

