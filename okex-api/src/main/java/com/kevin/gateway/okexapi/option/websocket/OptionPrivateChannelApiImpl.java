package com.kevin.gateway.okexapi.option.websocket;

import com.kevin.gateway.core.CoinPair;
import com.kevin.gateway.core.Credentials;
import com.kevin.gateway.okexapi.base.util.OkexEnvironment;
import com.kevin.gateway.okexapi.base.websocket.filters.WsZibDecompressStreamFilter;
import com.kevin.gateway.okexapi.base.websocket.request.SubscribeOpBuilder;
import com.kevin.gateway.okexapi.option.websocket.event.OptionAccountListener;
import com.kevin.gateway.okexapi.option.websocket.event.OptionOrderListener;
import com.kevin.gateway.okexapi.option.websocket.event.OptionPositionListener;
import com.kevin.gateway.okexapi.option.websocket.event.OptionPrivateDataListenerContainer;
import com.kevin.gateway.okexapi.option.websocket.response.AccountData;
import com.kevin.gateway.okexapi.option.websocket.response.OrderData;
import com.kevin.gateway.okexapi.option.websocket.response.PositionData;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OptionPrivateChannelApiImpl implements OptionPrivateChannelApi
        , OptionPositionListener
        , OptionAccountListener
        , OptionOrderListener {

    private final OptionPrivateDataListenerContainer optionPrivateDataListenerContainer;
    private final OkexEnvironment okexEnvironment;

    public OptionPrivateChannelApiImpl(OkexEnvironment okexEnvironment) {
        this.okexEnvironment = okexEnvironment;
        this.optionPrivateDataListenerContainer = new OptionPrivateDataListenerContainer();
        this.optionPrivateDataListenerContainer.addPositionListener(this);
        this.optionPrivateDataListenerContainer.addAccountListener(this);
        this.optionPrivateDataListenerContainer.addOrderListener(this);
    }

    @Override
    public void positionChannel(Credentials credentials, CoinPair... coinPairs) {
        SubscribeOpBuilder subscribeOpBuilder = new SubscribeOpBuilder();
        for (CoinPair coinPair : coinPairs) {
            subscribeOpBuilder.addSubscriptionTopic(OptionChannel.POSITION, coinPair);
        }
        OptionPrivateDataWsTemplate optionPrivateDataWsTemplate = new OptionPrivateDataWsTemplate(optionPrivateDataListenerContainer,
                subscribeOpBuilder,
                okexEnvironment,
                new WsZibDecompressStreamFilter(),
                credentials
        );
        optionPrivateDataWsTemplate.connect();
    }

    @Override
    public void accountChannel(Credentials credentials, CoinPair... coinPairs) {
        SubscribeOpBuilder subscribeOpBuilder = new SubscribeOpBuilder();
        for (CoinPair coinPair : coinPairs) {
            subscribeOpBuilder.addSubscriptionTopic(OptionChannel.ACCOUNT, coinPair);
        }
        OptionPrivateDataWsTemplate optionPrivateDataWsTemplate = new OptionPrivateDataWsTemplate(optionPrivateDataListenerContainer,
                subscribeOpBuilder,
                okexEnvironment,
                new WsZibDecompressStreamFilter(),
                credentials
        );
        optionPrivateDataWsTemplate.connect();
    }

    @Override
    public void orderChannel(Credentials credentials, CoinPair... coinPairs) {
        SubscribeOpBuilder subscribeOpBuilder = new SubscribeOpBuilder();
        for (CoinPair coinPair : coinPairs) {
            subscribeOpBuilder.addSubscriptionTopic(OptionChannel.ORDER, coinPair);
        }
        OptionPrivateDataWsTemplate optionPrivateDataWsTemplate = new OptionPrivateDataWsTemplate(optionPrivateDataListenerContainer,
                subscribeOpBuilder,
                okexEnvironment,
                new WsZibDecompressStreamFilter(),
                credentials
        );
        optionPrivateDataWsTemplate.connect();
    }

    @Override
    public void handleAccountData(AccountData accountData) {
        log.info("AccountData: {}", accountData);
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
