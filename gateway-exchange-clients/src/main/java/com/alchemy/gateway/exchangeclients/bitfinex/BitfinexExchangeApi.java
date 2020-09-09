package com.alchemy.gateway.exchangeclients.bitfinex;

import com.alchemy.gateway.core.AbstractExchangeApi;
import com.alchemy.gateway.core.common.CoinPairSymbolConverter;
import com.alchemy.gateway.core.info.ConnectionInfo;
import com.alchemy.gateway.core.info.ConnectionInfoImpl;
import com.alchemy.gateway.core.info.Features;
import com.alchemy.gateway.core.marketdata.MarketDataApi;
import com.alchemy.gateway.core.order.OrderApi;
import com.alchemy.gateway.core.wallet.AccountApi;
import com.alchemy.gateway.core.wallet.WalletApi;


public class BitfinexExchangeApi extends AbstractExchangeApi {

    public static final String NAME = "bitfinex";

    public static final String RESTFUL_API_ENDPOINT = "https://api.bitfinex.com/";

    public static final String RESTFUL_QUATATION="https://api-pub.bitfinex.com/v2/";

    public static final String WSS_API_ENDPOINT = "wss://api-pub.bitfinex.com/ws/2";


    private final ConnectionInfo connectionInfo;
    private final AccountApi accountApi;
    private final MarketDataApi marketDataApi;
    private final OrderApi orderApi;
    private final BitfinexCoinPairSymbolConverter coinPairConverter;


    public BitfinexExchangeApi() {


        this.connectionInfo = new ConnectionInfoImpl(RESTFUL_API_ENDPOINT, WSS_API_ENDPOINT);
        this.accountApi = new BitfinexAccountApi(this);
        this.marketDataApi = new BitfinexMarketDataApi(this);
        this.orderApi = new BitfinexOrderApi(this);
        this.coinPairConverter = new BitfinexCoinPairSymbolConverter();

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
