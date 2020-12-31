package com.kevin.gateway.core.json;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.kevin.gateway.core.Coin;
import com.kevin.gateway.core.CoinPair;
import com.kevin.gateway.core.FiatCoin;

public final class BaseModule extends SimpleModule {
    public BaseModule() {
        super();

        addSerializer(Coin.class, new CoinSerializer());
        addSerializer(CoinPair.class, new CoinPairSerializer());
        addSerializer(FiatCoin.class, new FiatCoinSerializer());

        addDeserializer(Coin.class, new CoinDeserializer());
        addDeserializer(CoinPair.class, new CoinPairDeserializer());
        addDeserializer(FiatCoin.class, new FiatCoinDeserializer());
    }
}
