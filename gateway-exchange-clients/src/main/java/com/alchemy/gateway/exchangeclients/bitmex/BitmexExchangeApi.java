package com.alchemy.gateway.exchangeclients.bitmex;

import com.alchemy.gateway.core.AbstractExchangeApi;
import com.alchemy.gateway.core.common.CoinPairSymbolConverter;
import com.alchemy.gateway.core.common.RateLimiterManager;
import com.alchemy.gateway.core.info.ConnectionInfo;
import com.alchemy.gateway.core.info.Features;
import com.alchemy.gateway.core.marketdata.MarketDataApi;
import com.alchemy.gateway.core.order.OrderApi;
import com.alchemy.gateway.core.wallet.AccountApi;
import com.alchemy.gateway.core.wallet.WalletApi;

public  class BitmexExchangeApi extends AbstractExchangeApi {

    public static final String NAME = "bitmex";
    private AccountApi accountApi;

    public BitmexExchangeApi(AccountApi accountApi) {
        this.accountApi = new BitmexAccountApi();
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public ConnectionInfo getConnectionInfo() {
        return null;
    }

    @Override
    public Features getFeatures() {
        return null;
    }

    @Override
    public CoinPairSymbolConverter getCoinPairSymbolConverter() {
        return null;
    }

    @Override
    public OrderApi getOrderApi() {
        return null;
    }

    @Override
    public AccountApi getAccountApi() {
        return this.accountApi;
    }

    @Override
    public WalletApi getWalletApi() {
        return null;
    }

    @Override
    public MarketDataApi getMarketDataApi() {
        return null;
    }

}
