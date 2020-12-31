package com.kevin.gateway.okexapi.base.websocket.response;

import com.kevin.gateway.okexapi.base.websocket.request.SubscriptionTopic;
import lombok.Data;

/**
 * 成功返回时的响应
 * <p>
 * 格式：{"event": "<value>","channel":"<value>"}
 * 例如：
 * <pre>
 *     {"event":"subscribe","channel":"spot/ticker:ETH-USDT"}
 *     {"event":"unsubscribe","channel":"spot/candle60s:BTC-USDT"}
 * </pre>
 */
@Data
public class EventResponse {
    String event;
    SubscriptionTopic channel;
}
