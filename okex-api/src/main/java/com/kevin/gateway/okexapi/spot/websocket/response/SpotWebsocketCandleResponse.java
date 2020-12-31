package com.kevin.gateway.okexapi.spot.websocket.response;

import com.kevin.gateway.okexapi.spot.vo.SpotCandleData;
import lombok.Data;

import java.util.List;

/**
 * 现货的K线数据响应
 * {
 *     "table":"spot/candle60s",
 *     "data":[
 *         {
 *             "candle":[
 *                 "2019-04-16T10:49:00.000Z",
 *                 "162.03",
 *                 "162.04",
 *                 "161.96",
 *                 "161.98",
 *                 "336.452694"
 *             ],
 *             "instrument_id":"ETH-USDT"
 *         }
 *     ]
 * }
 */
@Data
public class SpotWebsocketCandleResponse extends SpotWebsocketResponse {
    private List<SpotCandleData> data;
}

