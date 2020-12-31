package com.kevin.gateway.okexapi.option.websocket;

import com.kevin.gateway.core.CoinPair;
import com.kevin.gateway.core.Credentials;

public interface OptionPrivateChannelApi {

    void positionChannel(Credentials credentials, CoinPair... coinPairs);
    void accountChannel(Credentials credentials, CoinPair... coinPairs);
    void orderChannel(Credentials credentials, CoinPair... coinPairs);

}
