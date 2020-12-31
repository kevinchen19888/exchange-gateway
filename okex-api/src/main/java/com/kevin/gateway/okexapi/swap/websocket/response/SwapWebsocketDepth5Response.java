package com.kevin.gateway.okexapi.swap.websocket.response;

import com.kevin.gateway.okexapi.swap.vo.SwapDepthData;
import lombok.Data;

import java.util.List;

/**
 *  {
 *     "table": "swap/depth5",
 *     "data": [{
 *         "asks": [
 *             ["5621.7", "58", "0", "2"],
 *             ["5621.8", "125", "0", "5"],
 *             ["5622", "84", "0", "2"],
 *             ["5622.5", "6", "0", "1"],
 *             ["5623", "1", "0", "1"]
 *         ],
 *         "bids": [
 *             ["5621.3", "287", "0", "8"],
 *             ["5621.2", "41", "0", "1"],
 *             ["5621.1", "2", "0", "1"],
 *             ["5621", "26", "0", "2"],
 *             ["5620.9", "640", "0", "1"]
 *         ],
 *         "instrument_id": "BTC-USD-SWAP",
 *         "timestamp": "2019-05-06T07:03:33.048Z"
 *     }]
 * }
 */
@Data
public class SwapWebsocketDepth5Response extends SwapWebsocketResponse {
    private List<SwapDepthData> data;

}
