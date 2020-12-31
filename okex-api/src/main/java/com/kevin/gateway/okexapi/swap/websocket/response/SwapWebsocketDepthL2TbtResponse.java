package com.kevin.gateway.okexapi.swap.websocket.response;

import com.kevin.gateway.okexapi.base.util.DepthAction;
import com.kevin.gateway.okexapi.swap.vo.SwapDepthData;
import lombok.Data;

import java.util.List;

/**
 * 合约深度数据（depth5）响应
 * <p>
 * 格式：{"table":"channel",  "data":"[{"<value1>","<value2>"}]"}
 * <p>
 * 示例：
 * <pre>
 * First all entries：
 * {
 *     "table":"future/depth_l2_tbt",
 *     "action":"partial",
 *     "data":[
 *         {
 *             "instrument_id":"BTC-USD-200327",
 *             "asks":[
 *                 ["9887.69","0","0","0"],
 *                 ["9889.25","30","0","1"],
 *                 ["9890.01","0","0","0"]
 *             ],
 *             "bids":[
 *                 [ "9888.3","0","0","0"],
 *                 ["9878.22","0","0","0"],
 *                 ["9878.06","1","0","1"]
 *             ],
 *             "timestamp":"2020-02-06T03:35:42.509Z"
 *             "checksum":-2144245240
 *         }
 *     ]
 * }
 * </pre>
 */
@Data
public class SwapWebsocketDepthL2TbtResponse extends SwapWebsocketResponse {
    private List<SwapDepthData> data;
    DepthAction action;
}
