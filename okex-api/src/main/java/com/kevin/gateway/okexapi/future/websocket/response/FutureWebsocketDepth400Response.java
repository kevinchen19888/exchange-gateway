package com.kevin.gateway.okexapi.future.websocket.response;

import com.kevin.gateway.okexapi.base.util.DepthAction;
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
 *
 * 首次400档：
 * {
 *     "table": "future/depth",
 *     "action": "partial",
 *     "data": [{
 *         "instrument_id": "BTC-USD-191227",
 *         "asks": [
 *             ["8476.98","415","0","13"],
 *             ["8477","7","0","2"],
 *             ["8477.34","85","0","1"],
 *             ["8477.56","1","0","1"],
 *             ...
 *             ["8505.84","8","0","1"],
 *             ["8506.37","85","0","1"],
 *             ["8506.49","2","0","1"],
 *             ["8506.96","100","0","2"]
 *         ],
 *         "bids": [
 *             ["8476.97","256","0","12"],
 *             ["8475.55","101","0","1"],
 *             ["8475.54","100","0","1"],
 *             ["8475.3","1","0","1"],
 *             ...
 *             ["8447.32","6","0","1"],
 *             ["8447.02","246","0","1"],
 *             ["8446.83","24","0","1"],
 *             ["8446","95","0","3"]
 *         ],
 *         "timestamp": "2019-05-06T07:19:39.348Z",
 *         "checksum": -855196043
 *     }]
 * }
 *
 * 后续更新：
 * {
 *     "table":"future/depth",
 *     "action":"update",
 *     "data":[
 *         {
 *             "instrument_id":"BTC-USD-191227",
 *             "asks":[
 *                 ["8476.98", "375", "0", "13"],
 *                 ["8479.61", "0", "0", "0"],
 *                 ["8480.47", "0", "0", "0"],
 *                 ["8481.56", "12", "0", "2"],
 *                 ["8487.07", "0", "0", "0"],
 *                 ["8507", "4", "0", "2"],
 *                 ["8507.19", "142", "0", "2"],
 *                 ["8507.72", "3", "0", "1"]
 *             ],
 *             "bids":[
 *                 ["8470.59", "0", "0", "0"],
 *                 ["8467.44", "34", "0", "1"],
 *                 ["8465.33", "0", "0", "0"],
 *                 ["8463.79", "0", "0", "0"],
 *                 ["8447.02", "246", "0", "1"],
 *                 ["8446.83", "24", "0", "1"]
 *             ],
 *             "timestamp":"2019-09-26T08:47:43.730Z",
 *             "checksum":-253252232
 *         }
 *     ]
 * }
 * </pre>
 */
@Data
public class FutureWebsocketDepth400Response extends FutureWebsocketResponse {
    private List<FutureDepthData> data;
    DepthAction action;

}
