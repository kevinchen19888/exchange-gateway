package com.kevin.gateway.okexapi.future.websocket;

import com.kevin.gateway.core.Coin;
import com.kevin.gateway.core.Credentials;
import com.kevin.gateway.okexapi.base.util.OkexEnvironment;
import com.kevin.gateway.okexapi.base.websocket.filters.WsZibDecompressStreamFilter;
import com.kevin.gateway.okexapi.base.websocket.request.SubscribeOpBuilder;
import com.kevin.gateway.okexapi.future.FutureMarketId;
import com.kevin.gateway.okexapi.future.websocket.event.FuturePrivateDataListenerContainer;

import java.util.List;

public class FutureOrderPrivateChannel {

    private final FuturePrivateDataListenerContainer futurePrivateDataListenerContainer;
    private final OkexEnvironment okexEnvironment;
    private final FuturePrivateDataWsTemplate futurePrivateDataWsTemplate;
    private final List<FutureMarketId> futureMarketIds;
    private final Credentials credentials;

    public FutureOrderPrivateChannel(FuturePrivateDataListenerContainer futurePrivateDataListenerContainer
            , OkexEnvironment okexEnvironment
            , List<FutureMarketId> futureMarketIds
            , Credentials credentials) {
        this.futurePrivateDataListenerContainer = futurePrivateDataListenerContainer;
        this.okexEnvironment = okexEnvironment;
        this.futureMarketIds = futureMarketIds;
        this.credentials = credentials;
        SubscribeOpBuilder subscribeOpBuilder = new SubscribeOpBuilder();
        futureMarketIds.forEach(futureMarketId -> {
            subscribeOpBuilder.addSubscriptionTopic(FutureChannel.ORDER, futureMarketId);
        });
        this.futurePrivateDataWsTemplate = new FuturePrivateDataWsTemplate(this.futurePrivateDataListenerContainer,
                subscribeOpBuilder,
                okexEnvironment,
                new WsZibDecompressStreamFilter(),
                this.credentials
                );
        this.futurePrivateDataWsTemplate.connect();
    }
}
