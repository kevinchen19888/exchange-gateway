package com.kevin.gateway.okexapi.option.websocket.response;

import com.kevin.gateway.okexapi.base.util.DepthAction;
import com.kevin.gateway.okexapi.option.vo.OptionDepthData;
import lombok.Data;

import java.util.List;

/**
 *  期权400档增量数据响应
 * <p>
 * 格式：{"table":"channel",  "data":"[{"<value1>","<value2>"}]"}
 * <p>
 * 示例：
 * <pre>
 * First all entries：
 * {
 *     "table":"option/depth_l2_tbt",
 *     "action":"partial",
 *     "data":[
 *         {
 *             "instrument_id":"BTC-USD-200207-9500-C",
 *             "asks":[
 *                 ["0.023","0","0","0"],
 *                 ["0.0235","100","0","1"]
 *             ],
 *             "bids":[
 *                 ["0.022","100","0","1"]
 *             ],
 *             "timestamp":"2020-02-06T05:03:30.354Z"
 *             "checksum":-2144245240
 *         }
 *     ]
 * }
 * </pre>
 */
@Data
public class OptionWebsocketDepthL2TbtResponse extends OptionWebsocketResponse {
    private List<OptionDepthData> data;
    DepthAction action;

}
