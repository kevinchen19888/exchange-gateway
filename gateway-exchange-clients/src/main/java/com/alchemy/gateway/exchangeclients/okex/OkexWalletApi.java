package com.alchemy.gateway.exchangeclients.okex;

import com.alchemy.gateway.core.wallet.WalletApi;

public class OkexWalletApi implements WalletApi {
    private final OkexExchangeApi exchangeApi;

    public OkexWalletApi(OkexExchangeApi exchangeApi) {
        this.exchangeApi = exchangeApi;
    }
}
