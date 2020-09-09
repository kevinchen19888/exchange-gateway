package com.alchemy.gateway.exchangeclients.huobi;

import com.alchemy.gateway.core.AbstractExchangeApi;
import com.alchemy.gateway.core.common.CoinPairSymbolConverter;
import com.alchemy.gateway.core.info.ConnectionInfo;
import com.alchemy.gateway.core.info.ConnectionInfoImpl;
import com.alchemy.gateway.core.info.Features;
import com.alchemy.gateway.core.marketdata.MarketDataApi;
import com.alchemy.gateway.core.order.OrderApi;
import com.alchemy.gateway.core.wallet.AccountApi;
import com.alchemy.gateway.core.wallet.WalletApi;
import com.alchemy.gateway.exchangeclients.huobi.util.HuobiCoinPairSymbolConverter;

public class HuobiExchangeApi extends AbstractExchangeApi {

    public static final String NAME = "huobi";
    public static final String RESTFUL_API_ENDPOINT = "https://api.huobi.pro";
    public static final String WSS_API_ENDPOINT = "wss://api.huobi.pro/ws";

    private final ConnectionInfo connectionInfo;
    private final AccountApi accountApi;
    private final MarketDataApi marketDataApi;
    private final OrderApi orderApi;
    private final CoinPairSymbolConverter coinPairConverter;

    public HuobiExchangeApi() {
        connectionInfo = new ConnectionInfoImpl(RESTFUL_API_ENDPOINT, WSS_API_ENDPOINT);
        accountApi = new HuobiAccountApi(this);
        orderApi = new HuobiOrderApi(this);
        marketDataApi = new HuobiMarketDataApi(this);
        coinPairConverter = new HuobiCoinPairSymbolConverter();
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
        return null;
    }

    @Override
    public CoinPairSymbolConverter getCoinPairSymbolConverter() {
        return this.coinPairConverter;
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
        return null;
    }

    @Override
    public MarketDataApi getMarketDataApi() {
        return this.marketDataApi;
    }
}
