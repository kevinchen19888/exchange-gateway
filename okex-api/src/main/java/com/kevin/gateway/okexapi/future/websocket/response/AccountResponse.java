package com.kevin.gateway.okexapi.future.websocket.response;

import com.kevin.gateway.okexapi.base.websocket.request.ChannelTrait;
import lombok.Data;

import java.util.List;

@Data
public class AccountResponse {

    ChannelTrait table;
    List<AccountMapData> data;

}
