package com.kevin.gateway.okexapi.future.websocket.response;

import com.kevin.gateway.okexapi.base.websocket.request.ChannelTrait;
import lombok.Data;

import java.util.List;

@Data
public class PositionResponse {

    ChannelTrait table;
    List<PositionData> data;

}
