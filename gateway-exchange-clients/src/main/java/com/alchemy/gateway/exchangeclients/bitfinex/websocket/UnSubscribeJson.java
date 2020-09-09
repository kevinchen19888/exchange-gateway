package com.alchemy.gateway.exchangeclients.bitfinex.websocket;

import lombok.Data;



@Data
public class UnSubscribeJson {

    private String event;

    private Long chanId;


}
