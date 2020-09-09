package com.alchemy.gateway.quotation;

import com.alchemy.gateway.quotation.util.RabbitConfigUtils;
import com.alchemy.gateway.quotation.util.RoutingKey;
import org.junit.jupiter.api.Test;

class QuotationApplicationTests {

    @Test
    public void contextLoads() {
        //alchemy.gateway.kline.交易所名称,市场类型.币对.级别
        /*String routingKey = new RoutingKey(".")
                .addPart("alchemy")
                .addPart("gateway")
                .addPart("kline").
                        addPart("okex").
                        addPart("SPOT").
                        addPart("BTC/USDT").
                        addPart("0").toString();*/
        String routingKey = new RoutingKey(".")
                .addPart(RabbitConfigUtils.ALCHEMY)
                .addPart(RabbitConfigUtils.GATEWAY)
                .addPart(RabbitConfigUtils.ROUTING_KEY_KLINE)
                .addPart("okex").
                        addPart("SPOT").
                        addPart("BTC/USDT").
                        addPart("0").toString();
        System.out.println(routingKey);
    }

}
