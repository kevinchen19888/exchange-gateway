package com.alchemy.gateway.exchangeclients.bitfinex.websocket;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 *
 *                  *   event: 'subscribe',
 *                  *   channel: 'book',
 *                  *   symbol: 'tBTCUSD'
 *
 *                  {"event":"subscribed","channel":"candles","chanId":515,"key":"trade:14d:tBTCUSD"}
 *
*/

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WsSubscribeObj {

    private static ObjectMapper mapper=new ObjectMapper();

    private String event;

    private String channel;

    private String key;

    private String symbol;

    /**
     * order book 的精度，取值 Level of price aggregation (P0, P1, P2, P3, P4).
     * The default is P0
     */
    private String prec;


    public String toJson()  {
        try {
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return "WsSubcribeObj{" +
                "event='" + event + '\'' +
                ", channel='" + channel + '\'' +
                ", key='" + key + '\'' +
                ", symbol='" + symbol + '\'' +
                '}';
    }
}
