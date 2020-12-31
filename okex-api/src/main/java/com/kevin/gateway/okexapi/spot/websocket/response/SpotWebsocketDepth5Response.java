package com.kevin.gateway.okexapi.spot.websocket.response;

import com.kevin.gateway.okexapi.spot.vo.SpotDepthData;
import lombok.Data;

import java.util.List;

/**
 * 全量/增量返回的深度数据（depth400）响应
 * <p>
 * 格式：{"table":"channel", "action":"<action>", "data":"[{"<value1>","<value2>"}]"}
 * <p>
 * 示例：
 * <pre>
 * {
 *     "table":"spot/depth_l2_tbt",
 *     "action":"partial",
 *     "data":[
 *         {
 *             "instrument_id":"BTC-USDT",
 *             "asks":[
 *                 ["9580.3","0.20939963","0","2"],
 *                 ["9582.7","0.33242846","0","3"],
 *                 ["9583.9","0.41760039","0","1"]
 *             ],
 *             "bids":[
 *                 ["9576.7","0.31658067","0","2"],
 *                 ["9574.4","0.15659893","0","2"],
 *                 ["9574.2","0.0105","0","1"]
 *             ],
 *             "timestamp":"2020-02-06T03:35:42.492Z"
 *             "checksum":-2144245240
 *         }
 *     ]
 * }
 * </pre>
 */
@Data
public class SpotWebsocketDepth5Response extends SpotWebsocketResponse {
    private List<SpotDepthData> data;
}
