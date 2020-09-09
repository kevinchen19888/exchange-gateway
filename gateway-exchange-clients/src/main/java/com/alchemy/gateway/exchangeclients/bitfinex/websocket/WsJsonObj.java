package com.alchemy.gateway.exchangeclients.bitfinex.websocket;

import com.alchemy.gateway.core.common.CandleInterval;
import com.alchemy.gateway.exchangeclients.bitfinex.BiffinexFeatures;
import com.alchemy.gateway.exchangeclients.bitfinex.entity.BitfinexChannelEnum;
import com.alchemy.gateway.exchangeclients.bitfinex.entity.SymbolInterval;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WsJsonObj {

    private static ObjectMapper mapper=new ObjectMapper();

    private String event;

    private BitfinexChannelEnum channel;

    private String key;

    private String symbol;

    private Long chanId;

    private String prec;

    /**
     *
     * @return channel
     */
    public BitfinexChannelEnum getChannelEnum()
    {
        return this.channel;
    }


    /**
     *
     * @return  "key":"trade:14d:tBTCUSD"
     */
    public SymbolInterval getSymbolInterval()
    {
        SymbolInterval  symbolInterval=new SymbolInterval();
        if (key==null)
        {
            if (symbol!=null)
            {
                symbolInterval.setSymbol(BiffinexFeatures.convertUsd2Usdt(symbol.substring(1)));
                return symbolInterval;
            }
            return  null;
        }
        String []arr= key.split(":");

        symbolInterval.setInterval(CandleInterval.fromSymbol(arr[1]));
        symbolInterval.setSymbol(BiffinexFeatures.convertUsd2Usdt(arr[2]));

        return symbolInterval;

    }
    @Override
    public String toString() {
        return "WsPublicSubObj{" +
                "event='" + event + '\'' +
                ", channel=" + channel+
                ", key='" + key + '\'' +
                ", symbol='" + symbol + '\'' +
                ", chanId='" + chanId + '\'' +
                '}';
    }
}
