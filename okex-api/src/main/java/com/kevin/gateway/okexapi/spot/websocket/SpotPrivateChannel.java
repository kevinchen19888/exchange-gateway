package com.kevin.gateway.okexapi.spot.websocket;

import com.kevin.gateway.core.Coin;
import com.kevin.gateway.core.Credentials;
import com.kevin.gateway.okexapi.base.util.OkexEnvironment;
import com.kevin.gateway.okexapi.base.websocket.filters.WsZibDecompressStreamFilter;
import com.kevin.gateway.okexapi.base.websocket.request.SubscribeOpBuilder;
import com.kevin.gateway.okexapi.spot.SpotMarketId;
import com.kevin.gateway.okexapi.spot.websocket.event.SpotPrivateDataListenerContainer;

import java.io.IOException;
import java.util.List;

public class SpotPrivateChannel {

    private final SpotPrivateDataListenerContainer spotPrivateDataListenerContainer;
    private final OkexEnvironment okexEnvironment;
    private final SpotPrivateDataWsTemplate spotPrivateDataWsTemplate;
    private final List<Coin> coins;
    private final List<SpotMarketId> spotMarketIds;
    private final Credentials credentials;

    public SpotPrivateChannel(SpotPrivateDataListenerContainer spotPrivateDataListenerContainer
            , OkexEnvironment okexEnvironment
            , List<Coin> coins
            , List<SpotMarketId> spotMarketIds
            , Credentials credentials) {
        this.spotPrivateDataListenerContainer = spotPrivateDataListenerContainer;
        this.okexEnvironment = okexEnvironment;
        this.coins = coins;
        this.spotMarketIds = spotMarketIds;
        this.credentials = credentials;
        SubscribeOpBuilder subscribeOpBuilder = new SubscribeOpBuilder();
        coins.forEach(coin -> { subscribeOpBuilder.addSubscriptionTopic(SpotChannel.ACCOUNT, coin);});
        spotMarketIds.forEach(spotMarketId -> {
            subscribeOpBuilder.addSubscriptionTopic(SpotChannel.MARGIN_ACCOUNT, spotMarketId);
            subscribeOpBuilder.addSubscriptionTopic(SpotChannel.ORDER_ALGO, spotMarketId);
            subscribeOpBuilder.addSubscriptionTopic(SpotChannel.ORDER, spotMarketId);
        });
        this.spotPrivateDataWsTemplate = new SpotPrivateDataWsTemplate(spotPrivateDataListenerContainer,
                subscribeOpBuilder,
                okexEnvironment,
                new WsZibDecompressStreamFilter(),
                this.credentials
        );
        this.spotPrivateDataWsTemplate.connect();
    }

    public void stop() {
        try {
            spotPrivateDataWsTemplate.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
