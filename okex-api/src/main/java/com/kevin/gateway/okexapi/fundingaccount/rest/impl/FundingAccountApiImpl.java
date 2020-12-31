package com.kevin.gateway.okexapi.fundingaccount.rest.impl;

import com.kevin.gateway.core.Coin;
import com.kevin.gateway.core.Credentials;
import com.kevin.gateway.okexapi.base.util.AccountType;
import com.kevin.gateway.okexapi.base.util.OkexEnvironment;
import com.kevin.gateway.okexapi.base.rest.PrivateGetTemplate;
import com.kevin.gateway.okexapi.base.rest.PrivateGetTemplateClient;
import com.kevin.gateway.okexapi.base.rest.PrivatePostTemplate;
import com.kevin.gateway.okexapi.base.rest.PrivatePostTemplateClient;
import com.kevin.gateway.okexapi.base.rest.impl.OkexAbstractImpl;
import com.kevin.gateway.okexapi.base.util.OkexPagination;
import com.kevin.gateway.okexapi.base.util.OperationType;
import com.kevin.gateway.okexapi.fundingaccount.domain.*;
import com.kevin.gateway.okexapi.fundingaccount.rest.FundingAccountApi;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.SneakyThrows;
import org.springframework.lang.Nullable;

import java.time.Duration;
import java.util.Objects;

public class FundingAccountApiImpl extends OkexAbstractImpl implements FundingAccountApi {
    private static final PrivateGetTemplate GET_WALLET_INFOS = PrivateGetTemplate
            .of("/api/account/v3/wallet", 6, Duration.ofSeconds(1L));
    private static final PrivateGetTemplate GET_WALLET_INFO = PrivateGetTemplate
            .of("/api/account/v3/wallet/{currency}", 6, Duration.ofSeconds(1L));
    private static final PrivateGetTemplate GET_LEDGER_INFOS = PrivateGetTemplate
            .of("/api/account/v3/ledger", 6, Duration.ofSeconds(1L));
    private static final PrivateGetTemplate GET_DEPOSIT_ADDRESS = PrivateGetTemplate
            .of("/api/account/v3/deposit/address", 6, Duration.ofSeconds(1L));
    private static final PrivateGetTemplate GET_ALL_DEPOSIT_HISTORY = PrivateGetTemplate
            .of("/api/account/v3/deposit/history", 6, Duration.ofSeconds(1L));
    private static final PrivateGetTemplate GET_DEPOSIT_HISTORY = PrivateGetTemplate
            .of("/api/account/v3/deposit/history/{currency}", 6, Duration.ofSeconds(1L));
    private static final PrivateGetTemplate GET_CURRENCIES_INFO = PrivateGetTemplate
            .of("/api/account/v3/currencies", 6, Duration.ofSeconds(1L));
    private static final PrivateGetTemplate GET_WITHDRAWAL_FEES = PrivateGetTemplate
            .of("/api/account/v3/withdrawal/fee", 6, Duration.ofSeconds(1L));
    private static final PrivateGetTemplate GET_ASSET_VALUATION = PrivateGetTemplate
            .of("/api/account/v3/asset-valuation", 1, Duration.ofSeconds(30L));
    private static final PrivateGetTemplate GET_SUB_ACCOUNT_INFO = PrivateGetTemplate
            .of("/api/account/v3/sub-account", 1, Duration.ofSeconds(30L));
    private static final PrivateGetTemplate GET_ALL_WITHDRAWAL_HISTORY = PrivateGetTemplate
            .of("/api/account/v3/withdrawal/history", 6, Duration.ofSeconds(1L));
    private static final PrivateGetTemplate GET_WITHDRAWAL_HISTORY = PrivateGetTemplate
            .of("/api/account/v3/withdrawal/history/{currency}", 6, Duration.ofSeconds(1L));
    private static final PrivatePostTemplate POST_ASSET_TRANSFER = PrivatePostTemplate
            .of("/api/account/v3/transfer", 1, Duration.ofSeconds(1L));
    private static final PrivatePostTemplate POST_WITHDRAWAL = PrivatePostTemplate
            .of("/api/account/v3/withdrawal", 6, Duration.ofSeconds(1L));

    public FundingAccountApiImpl(OkexEnvironment environment) {
        super(environment);
    }

    @Override
    public WalletResponse[] getWallets(Credentials credentials) {
        PrivateGetTemplateClient client = GET_WALLET_INFOS.bind(environment, credentials);
        return client.getForObject(WalletResponse[].class);
    }

    @Override
    public WalletResponse[] getWalletInfo(Credentials credentials, Coin currency) {
        PrivateGetTemplateClient client = GET_WALLET_INFO.bind(environment, credentials);
        return client.getForObject(WalletResponse[].class, currency.getSymbol());
    }

    @Override
    public TransferResponse assetTransfer(Credentials credentials, AssetTransferRequest req) {
        PrivatePostTemplateClient client = POST_ASSET_TRANSFER.bind(environment, credentials);
        return client.postForObject(req, TransferResponse.class);
    }

    @Override
    public WithdrawalResponse withdraw(Credentials credentials, WithdrawalRequest req) {
        PrivatePostTemplateClient client = POST_WITHDRAWAL.bind(environment, credentials);
        return client.postForObject(req, WithdrawalResponse.class);
    }

    @Override
    public LedgerResponse[] getLedgerInfos(Credentials credentials, @Nullable Coin currency,
                                           @Nullable OperationType type, OkexPagination pagination) {
        PrivateGetTemplateClient client = GET_LEDGER_INFOS.bind(environment, credentials);
        if (currency != null) {
            client.getUriComponentsBuilder().queryParam("currency", currency.getSymbol());
        }
        if (pagination != null) {
            if (pagination.getAfter() != null) {
                client.getUriComponentsBuilder().queryParam("after", pagination.getAfter());
            }
            if (pagination.getBefore() != null) {
                client.getUriComponentsBuilder().queryParam("before", pagination.getBefore());
            }
            if (pagination.getLimit() != null) {
                client.getUriComponentsBuilder().queryParam("limit", pagination.getLimit());
            }
        }
        if (type != null) {
            client.getUriComponentsBuilder().queryParam("type", type.getVal());
        }
        return client.getForObject(LedgerResponse[].class);
    }

    @Override
    public DepositAddressResponse[] getDepositAddress(Credentials credentials, Coin currency) {
        PrivateGetTemplateClient client = GET_DEPOSIT_ADDRESS.bind(environment, credentials);
        if (currency != null) {
            client.getUriComponentsBuilder().queryParam("currency", currency.getSymbol());
        }
        return client.getForObject(DepositAddressResponse[].class);
    }

    @Override
    public AssetValuationResponse getAssetValuation(Credentials credentials, @Nullable AccountType accountType, @Nullable Coin valuationCurrency) {
        PrivateGetTemplateClient client = GET_ASSET_VALUATION.bind(environment, credentials);
        if (accountType != null) {
            client.getUriComponentsBuilder().queryParam("account_type", accountType.getVal());
        }
        if (valuationCurrency != null) {
            client.getUriComponentsBuilder().queryParam("valuation_currency", valuationCurrency.getSymbol());
        }
        return client.getForObject(AssetValuationResponse.class);
    }

    @Override
    @SneakyThrows
    public SubAccountResponse getSubAccountInfo(Credentials credentials, String subAccountName) {
        PrivateGetTemplateClient client = GET_SUB_ACCOUNT_INFO.bind(environment, credentials);
        client.getUriComponentsBuilder().queryParam("sub-account", subAccountName);

        JsonNode data = Objects.requireNonNull(client.getForObject(JsonNode.class)).get("data");
        if (data != null) {
            return this.environment.getObjectMapper().treeToValue(data, SubAccountResponse.class);
        }
        return new SubAccountResponse();
    }

    @Override
    public WithdrawalHistoryResponse[] getAllWithdrawalHistory(Credentials credentials) {
        PrivateGetTemplateClient client = GET_ALL_WITHDRAWAL_HISTORY.bind(environment, credentials);
        return client.getForObject(WithdrawalHistoryResponse[].class);
    }

    @Override
    public WithdrawalHistoryResponse[] getWithdrawalHistory(Credentials credentials, Coin currency) {
        PrivateGetTemplateClient client = GET_WITHDRAWAL_HISTORY.bind(environment, credentials);
        return client.getForObject(WithdrawalHistoryResponse[].class, currency.getSymbol());
    }

    @Override
    public DepositHistoryResponse[] getAllDepositHistory(Credentials credentials) {
        PrivateGetTemplateClient client = GET_ALL_DEPOSIT_HISTORY.bind(environment, credentials);
        return client.getForObject(DepositHistoryResponse[].class);
    }

    @Override
    public DepositHistoryResponse[] getDepositHistory(Credentials credentials, Coin currency,
                                                      @Nullable Long after, @Nullable Long before, @Nullable Integer limit) {

        PrivateGetTemplateClient client = GET_DEPOSIT_HISTORY.bind(environment, credentials);
        if (before != null) {
            client.getUriComponentsBuilder().queryParam("before", before);
        }
        if (after != null) {
            client.getUriComponentsBuilder().queryParam("after", after);
        }
        if (limit != null) {
            client.getUriComponentsBuilder().queryParam("limit", limit);
        }
        return client.getForObject(DepositHistoryResponse[].class, currency.getSymbol());
    }

    @Override
    public CurrencyResponse[] getCurrenciesInfo(Credentials credentials) {
        PrivateGetTemplateClient client = GET_CURRENCIES_INFO.bind(environment, credentials);
        return client.getForObject(CurrencyResponse[].class);
    }

    @Override
    public WithdrawalFeeResponse[] getWithdrawalFees(Credentials credentials, Coin currency) {
        PrivateGetTemplateClient client = GET_WITHDRAWAL_FEES.bind(environment, credentials);
        if (currency != null) {
            client.getUriComponentsBuilder().queryParam("currency", currency.getSymbol());
        }
        return client.getForObject(WithdrawalFeeResponse[].class);
    }

}
