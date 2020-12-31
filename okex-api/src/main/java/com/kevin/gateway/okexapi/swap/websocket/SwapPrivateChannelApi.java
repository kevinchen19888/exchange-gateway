package com.kevin.gateway.okexapi.swap.websocket;

import com.kevin.gateway.core.Credentials;
import com.kevin.gateway.okexapi.swap.SwapMarketId;

public interface SwapPrivateChannelApi {

    void accountChannel(Credentials credentials, SwapMarketId... swapMarketIds);
    void positionChannel(Credentials credentials, SwapMarketId... swapMarketIds);
    void orderAlgoChannel(Credentials credentials, SwapMarketId... swapMarketIds);
    void orderChannel(Credentials credentials, SwapMarketId... swapMarketIds);

}
