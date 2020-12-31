package com.kevin.gateway.okexapi.future.websocket.response;

import com.kevin.gateway.okexapi.future.vo.FutureDepthData;
import lombok.Data;

import java.util.List;

/**
 * 合约深度数据（depth5）响应
 * <p>
 * 格式：{"table":"channel",  "data":"[{"<value1>","<value2>"}]"}
 * <p>
 * 示例：
 * <pre>
 * { *
 *     "table": "future/depth5",
 *     "data": [{
 *         "asks": [
 *             ["5556.82", "11", "0", "1"],
 *             ["5556.84", "98", "0", "4"],
 *             ["5556.92", "1", "0", "1"],
 *             ["5557.6", "4", "0", "1"],
 *             ["5557.85", "2", "0", "1"]
 *         ],
 *         "bids": [
 *             ["5556.81", "1", "0", "1"],
 *             ["5556.8", "2", "0", "1"],
 *             ["5556.79", "1", "0", "1"],
 *             ["5556.19", "100", "0", "1"],
 *             ["5556.08", "2", "0", "1"]
 *         ],
 *         "instrument_id": "BTC-USD-190628",
 *         "timestamp": "2019-05-06T07:19:39.348Z"
 *     }]
 * }
 * </pre>
 */
@Data
public class FutureWebsocketDepth5Response extends FutureWebsocketResponse {
    private List<FutureDepthData> data;


}
