package com.alchemy.gateway.exchangeclients.binance;

import com.alchemy.gateway.core.AbstractExchangeApi;
import com.alchemy.gateway.core.common.CoinPairSymbolConverter;
import com.alchemy.gateway.core.info.ConnectionInfo;
import com.alchemy.gateway.core.info.ConnectionInfoImpl;
import com.alchemy.gateway.core.info.Features;
import com.alchemy.gateway.core.marketdata.MarketDataApi;
import com.alchemy.gateway.core.order.OrderApi;
import com.alchemy.gateway.core.wallet.AccountApi;
import com.alchemy.gateway.core.wallet.WalletApi;
import com.alchemy.gateway.exchangeclients.binance.util.BinanceCoinPairSymbolConverter;

public class BinanceExchangeApi extends AbstractExchangeApi {

    public static final String NAME = "binance";
    public static final String RESTFUL_API_ENDPOINT = "https://api.binance.com";
    public static final String WSS_API_ENDPOINT = "wss://stream.binance.com:9443";
    /**
     * binance api请求时延抖动最大允许窗口
     */
    public final String recvWindow = "30000";
    private final ConnectionInfo connectionInfo;
    private final AccountApi accountApi;
    private final MarketDataApi marketDataApi;
    private final OrderApi orderApi;
    private final BinanceCoinPairSymbolConverter coinPairConverter;

    public BinanceExchangeApi() {
        this.connectionInfo = new ConnectionInfoImpl(RESTFUL_API_ENDPOINT, WSS_API_ENDPOINT);
        this.accountApi = new BinanceAccountApi(this);
        this.marketDataApi = new BinanceMarketDataApi(this);
        this.orderApi = new BinanceOrderApi(this);
        this.coinPairConverter = new BinanceCoinPairSymbolConverter();
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
