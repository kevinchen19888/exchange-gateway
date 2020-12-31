package com.kevin.gateway.okexapi.swap.websocket.response;

import com.kevin.gateway.okexapi.base.websocket.request.ChannelTrait;
import lombok.Data;

import java.util.List;

@Data
public class OrderResponse {

    ChannelTrait table;
    List<OrderData> data;

}
