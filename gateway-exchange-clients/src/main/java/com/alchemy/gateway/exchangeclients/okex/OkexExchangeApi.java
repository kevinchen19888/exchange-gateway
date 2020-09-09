package com.alchemy.gateway.exchangeclients.okex;

import com.alchemy.gateway.core.AbstractExchangeApi;
import com.alchemy.gateway.core.common.CoinPairSymbolConverter;
import com.alchemy.gateway.core.common.RateLimiterManager;
import com.alchemy.gateway.core.info.ConnectionInfo;
import com.alchemy.gateway.core.info.ConnectionInfoImpl;
import com.alchemy.gateway.core.info.Features;
import com.alchemy.gateway.core.marketdata.MarketDataApi;
import com.alchemy.gateway.core.order.OrderApi;
import com.alchemy.gateway.core.wallet.AccountApi;
import com.alchemy.gateway.core.wallet.WalletApi;
import com.alchemy.gateway.exchangeclients.okex.impl.OkexCoinPairSymbolConverter;
import com.alchemy.gateway.exchangeclients.okex.impl.OkexFeatures;

public class OkexExchangeApi extends AbstractExchangeApi {

    public static final String NAME = "okex";
    public static final String RESTFUL_API_ENDPOINT = "https://www.okex.com";
    public static final String WSS_API_ENDPOINT = "wss://real.okex.com:8443/ws/v3";

    private final Features features;
    private final CoinPairSymbolConverter coinPairSymbolConverter;
    private final ConnectionInfo connectionInfo;
    private final MarketDataApi marketDataApi;
    private final AccountApi accountApi;
    private final OrderApi orderApi;
    private final WalletApi walletApi;

    public OkexExchangeApi() {
        this.connectionInfo = new ConnectionInfoImpl(RESTFUL_API_ENDPOINT, WSS_API_ENDPOINT);
        this.coinPairSymbolConverter = new OkexCoinPairSymbolConverter();
        this.features = new OkexFeatures();
        this.marketDataApi = new OkexMarketDataApi(this);
        this.orderApi = new OkexOrderApi(this);
        this.accountApi = new OkexAccountApi(this);
        this.walletApi = new OkexWalletApi(this);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public ConnectionInfo getConnectionInfo() {
        return this.connectionInfo;
    }

    @Override
    public Features getFeatures() {
        return this.features;
    }

    @Override
    public CoinPairSymbolConverter getCoinPairSymbolConverter() {
        return this.coinPairSymbolConverter;
    }

    @Override
    public OrderApi getOrderApi() {
        return this.orderApi;
    }

    @Override
    public AccountApi getAccountApi() {
        return this.accountApi;
    }

    @Override
    public WalletApi getWalletApi() {
        return this.walletApi;
    }

    @Override
    public MarketDataApi getMarketDataApi() {
        return this.marketDataApi;
    }

}
