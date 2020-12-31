package com.kevin.gateway.okexapi.swap.websocket;

import com.kevin.gateway.core.Credentials;
import com.kevin.gateway.okexapi.base.util.OkexEnvironment;
import com.kevin.gateway.okexapi.base.websocket.filters.WsZibDecompressStreamFilter;
import com.kevin.gateway.okexapi.base.websocket.request.SubscribeOpBuilder;
import com.kevin.gateway.okexapi.swap.SwapMarketId;
import com.kevin.gateway.okexapi.swap.websocket.event.*;
import com.kevin.gateway.okexapi.swap.websocket.response.AccountData;
import com.kevin.gateway.okexapi.swap.websocket.response.OrderAlgoData;
import com.kevin.gateway.okexapi.swap.websocket.response.OrderData;
import com.kevin.gateway.okexapi.swap.websocket.response.PositionData;
import lombok.extern.slf4j.Slf4j;
@Slf4j
public class SwapPrivateChannelApiImpl implements SwapPrivateChannelApi
        , SwapPositionListener
        , SwapAccountListener
        , SwapOrderListener
        , SwapOrderAlgoListener {

    private final SwapPrivateDataListenerContainer swapPrivateDataListenerContainer;
    private final OkexEnvironment okexEnvironment;

    public SwapPrivateChannelApiImpl(OkexEnvironment okexEnvironment) {
        this.okexEnvironment = okexEnvironment;
        this.swapPrivateDataListenerContainer = new SwapPrivateDataListenerContainer();
        this.swapPrivateDataListenerContainer.addPositionListener(this);
        this.swapPrivateDataListenerContainer.addAccountListener(this);
        this.swapPrivateDataListenerContainer.addOrderListener(this);
        this.swapPrivateDataListenerContainer.addOrderAlgoListener(this);
    }

    @Override
    public void accountChannel(Credentials credentials, SwapMarketId... swapMarketIds) {
        SubscribeOpBuilder subscribeOpBuilder = new SubscribeOpBuilder();
        for (SwapMarketId swapMarketId : swapMarketIds) {
            subscribeOpBuilder.addSubscriptionTopic(SwapChannel.ACCOUNT, swapMarketId);
        }
        SwapPrivateDataWsTemplate swapPrivateDataWsTemplate = new SwapPrivateDataWsTemplate(swapPrivateDataListenerContainer,
                subscribeOpBuilder,
                okexEnvironment,
                new WsZibDecompressStreamFilter(),
                credentials
        );
        swapPrivateDataWsTemplate.connect();
    }

    @Override
    public void positionChannel(Credentials credentials, SwapMarketId... swapMarketIds) {
        SubscribeOpBuilder subscribeOpBuilder = new SubscribeOpBuilder();
        for (SwapMarketId swapMarketId : swapMarketIds) {
            subscribeOpBuilder.addSubscriptionTopic(SwapChannel.POSITION, swapMarketId);
        }
        SwapPrivateDataWsTemplate swapPrivateDataWsTemplate = new SwapPrivateDataWsTemplate(swapPrivateDataListenerContainer,
                subscribeOpBuilder,
                okexEnvironment,
                new WsZibDecompressStreamFilter(),
                credentials
        );
        swapPrivateDataWsTemplate.connect();
    }

    @Override
    public void orderAlgoChannel(Credentials credentials, SwapMarketId... swapMarketIds) {
        SubscribeOpBuilder subscribeOpBuilder = new SubscribeOpBuilder();
        for (SwapMarketId swapMarketId : swapMarketIds) {
            subscribeOpBuilder.addSubscriptionTopic(SwapChannel.ORDER_ALGO, swapMarketId);
        }
        SwapPrivateDataWsTemplate swapPrivateDataWsTemplate = new SwapPrivateDataWsTemplate(swapPrivateDataListenerContainer,
                subscribeOpBuilder,
                okexEnvironment,
                new WsZibDecompressStreamFilter(),
                credentials
        );
        swapPrivateDataWsTemplate.connect();
    }

    @Override
    public void orderChannel(Credentials credentials, SwapMarketId... swapMarketIds) {
        SubscribeOpBuilder subscribeOpBuilder = new SubscribeOpBuilder();
        for (SwapMarketId swapMarketId : swapMarketIds) {
            subscribeOpBuilder.addSubscriptionTopic(SwapChannel.ORDER, swapMarketId);
        }
        SwapPrivateDataWsTemplate swapPrivateDataWsTemplate = new SwapPrivateDataWsTemplate(swapPrivateDataListenerContainer,
                subscribeOpBuilder,
                okexEnvironment,
                new WsZibDecompressStreamFilter(),
                credentials
        );
        swapPrivateDataWsTemplate.connect();
    }

    @Override
    public void handleAccountData(AccountData accountData) {
        log.info("AccountData: {}", accountData);
    }

    @Override
    public void handleOrderAlgoData(OrderAlgoData orderAlgoData) {
        log.info("OrderAlgoData: {}", orderAlgoData);
    }

    @Override
    public void handleOrderData(OrderData orderData) {
        log.info("OrderData: {}", orderData);
    }

    @Override
    public void handlePositionData(PositionData positionData) {
        log.info("PositionData: {}", positionData);
    }
}
