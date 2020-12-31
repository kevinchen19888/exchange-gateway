package com.kevin.gateway.huobiapi.spot;

import com.kevin.gateway.core.Credentials;
import com.kevin.gateway.core.FiatCoin;
import com.kevin.gateway.huobiapi.base.HuobiEnvironment;
import com.kevin.gateway.huobiapi.base.rest.*;
import com.kevin.gateway.huobiapi.base.rest.*;
import com.kevin.gateway.huobiapi.base.rest.impl.HuobiAbstractImpl;
import com.kevin.gateway.huobiapi.base.util.CandleInterval;
import com.kevin.gateway.huobiapi.spot.model.SpotAccountType;
import com.kevin.gateway.huobiapi.spot.model.SpotDepthType;
import com.kevin.gateway.huobiapi.spot.model.SpotTransactType;
import com.kevin.gateway.huobiapi.spot.request.SpotAccountTransferRequest;
import com.kevin.gateway.huobiapi.spot.request.SpotApiWindow;
import com.kevin.gateway.huobiapi.spot.request.SpotFuturesTransferRequest;
import com.kevin.gateway.huobiapi.spot.request.SpotPointTransferRequest;
import com.kevin.gateway.huobiapi.spot.response.account.*;
import com.kevin.gateway.huobiapi.spot.response.account.*;
import com.kevin.gateway.huobiapi.spot.response.baseInfo.SpotCommonCurrencysResponse;
import com.kevin.gateway.huobiapi.spot.response.baseInfo.SpotMarketStatusResponse;
import com.kevin.gateway.huobiapi.spot.response.baseInfo.SpotSymbolsResponse;
import com.kevin.gateway.huobiapi.spot.response.marketData.*;
import com.kevin.gateway.huobiapi.spot.vo.*;
import com.kevin.gateway.huobiapi.spot.response.marketData.*;
import com.kevin.gateway.huobiapi.spot.vo.*;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class SpotApiImpl extends HuobiAbstractImpl implements SpotApi {

    private static final String SYMBOL = "symbol";


    public SpotApiImpl(HuobiEnvironment environment) {
        super(environment);
    }

    private static final PublicGetTemplate SEARCH_MARKET_STATUS = PublicGetTemplate.
            of("/v2/market-status", 10, Duration.ofSeconds(1L));

    @Override
    public SpotMarketStatusVo searchMarketStatus() {
        PublicGetTemplateClient client = SEARCH_MARKET_STATUS.bind(environment);
        SpotMarketStatusResponse response = client.getForObject(SpotMarketStatusResponse.class);
        if (response != null && response.getData() != null) {
            return response.getData();
        }
        return new SpotMarketStatusVo();
    }

    private static final PublicGetTemplate SEARCH_COMMON_SYMBOLS = PublicGetTemplate.
            of("/v1/common/symbols", 10, Duration.ofSeconds(1L));

    @Override
    public List<SpotSymbolsVo> searchCommonSymbols() {
        PublicGetTemplateClient client = SEARCH_COMMON_SYMBOLS.bind(environment);
        SpotSymbolsResponse response = client.getForObject(SpotSymbolsResponse.class);
        if (response != null && response.getData() != null) {
            return response.getData();
        }
        return Collections.emptyList();
    }

    private static final PublicGetTemplate SEARCH_COMMON_CURRENCYS = PublicGetTemplate.
            of("/v1/common/currencys", 10, Duration.ofSeconds(1L));

    @Override
    public List<SpotCoin> searchCommonCurrencys() {
        PublicGetTemplateClient client = SEARCH_COMMON_CURRENCYS.bind(environment);
        SpotCommonCurrencysResponse response = client.getForObject(SpotCommonCurrencysResponse.class);
        if (response != null && response.getData() != null) {
            return response.getData();
        }
        return Collections.emptyList();
    }

    private static final PublicGetTemplate SEARCH_KLINE = PublicGetTemplate.
            of("/market/history/kline", 10, Duration.ofSeconds(1L));

    @Override
    public List<SpotKlineVo> searchKline(SpotMarketId marketId, CandleInterval period, SpotApiWindow spotApiWindow) {
        PublicGetTemplateClient client = SEARCH_KLINE.bind(environment);
        client.getUriComponentsBuilder().queryParam(SYMBOL, marketId.getSymbol())
                .queryParam("period", period.getSymbol());
        checkOkexApiWindow(spotApiWindow, client);

        SpotKlineResponse response = client.getForObject(SpotKlineResponse.class);
        if (response != null && response.getData() != null) {
            return response.getData();
        }
        return Collections.emptyList();
    }

    private static final PublicGetTemplate SEARCH_TICKER = PublicGetTemplate.
            of("/market/detail/merged", 10, Duration.ofSeconds(1L));

    @Override
    public SpotTickerVo searchTicker(SpotMarketId marketId) {
        PublicGetTemplateClient client = SEARCH_TICKER.bind(environment);
        client.getUriComponentsBuilder().queryParam(SYMBOL, marketId.getSymbol());
        SpotTickerResponse response = client.getForObject(SpotTickerResponse.class);
        if (response != null && response.getTick() != null) {
            return response.getTick();
        }
        return new SpotTickerVo();
    }

    private static final PublicGetTemplate SEARCH_LATEST_TICKERS_FOR_ALL_PAIRS = PublicGetTemplate.
            of("/market/tickers", 10, Duration.ofSeconds(1L));

    @Override
    public List<SpotLatestTickersForAllPairsVo> searchLatestTickersForAllPairs() {
        PublicGetTemplateClient client = SEARCH_LATEST_TICKERS_FOR_ALL_PAIRS.bind(environment);
        SpotLatestTickersForAllPairsResponse response = client.getForObject(SpotLatestTickersForAllPairsResponse.class);
        if (response != null && response.getData() != null) {
            return response.getData();
        }
        return Collections.emptyList();
    }

    private static final PublicGetTemplate SEARCH_DEPTH = PublicGetTemplate.
            of("/market/depth", 10, Duration.ofSeconds(1L));

    @Override
    public SpotDepthVo searchDepth(SpotMarketId marketId, BigDecimal depth, SpotDepthType type) {
        PublicGetTemplateClient client = SEARCH_DEPTH.bind(environment);
        client.getUriComponentsBuilder().queryParam(SYMBOL, marketId.getSymbol())
                .queryParam("type", type.getType());
        if (depth.compareTo(BigDecimal.ZERO) != 0) client.getUriComponentsBuilder().queryParam("depth", depth);
        SpotDepthResponse response = client.getForObject(SpotDepthResponse.class);
        if (response != null && response.getTick() != null) {
            return response.getTick();
        }
        return new SpotDepthVo();
    }

    private static final PublicGetTemplate SEARCH_TRADE = PublicGetTemplate.
            of("/market/trade", 10, Duration.ofSeconds(1L));

    @Override
    public List<SpotTradeVo> searchTrade(SpotMarketId marketId) {
        PublicGetTemplateClient client = SEARCH_TRADE.bind(environment);
        client.getUriComponentsBuilder().queryParam(SYMBOL, marketId.getSymbol());
        SpotTradeResponse response = client.getForObject(SpotTradeResponse.class);
        if (response != null && response.getTick() != null) {
            return response.getTick().getData();
        }
        return Collections.emptyList();
    }

    private static final PublicGetTemplate SEARCH_HISTORY_TRADE = PublicGetTemplate.
            of("/market/history/trade", 10, Duration.ofSeconds(1L));

    @Override
    public List<SpotHistoryTradeVo> searchHistoryTrade(SpotMarketId marketId, SpotApiWindow spotApiWindow) {
        PublicGetTemplateClient client = SEARCH_HISTORY_TRADE.bind(environment);
        client.getUriComponentsBuilder().queryParam(SYMBOL, marketId.getSymbol());
        checkOkexApiWindow(spotApiWindow, client);
        SpotHistoryTradeResponse response = client.getForObject(SpotHistoryTradeResponse.class);
        if (response != null && response.getData() != null) {
            return response.getData();
        }
        return Collections.emptyList();
    }

    private static final PublicGetTemplate SEARCH_MARKET_DETAIL = PublicGetTemplate.
            of("/market/detail", 10, Duration.ofSeconds(1L));

    @Override
    public SpotLast24hMarketSummaryVo searchMarketDetail(SpotMarketId marketId) {
        PublicGetTemplateClient client = SEARCH_MARKET_DETAIL.bind(environment);
        client.getUriComponentsBuilder().queryParam(SYMBOL, marketId.getSymbol());
        SpotLast24hMarketSummaryResponse response = client.getForObject(SpotLast24hMarketSummaryResponse.class);
        if (response != null && response.getTick() != null) {
            return response.getTick();
        }
        return new SpotLast24hMarketSummaryVo();
    }

    private static final PublicGetTemplate SEARCH_MARKET_ETP = PublicGetTemplate.
            of("/market/etp", 10, Duration.ofSeconds(1L));

    @Override
    public SpotRealTimeNavVo searchMarketEtp(SpotMarketId marketId) {
        PublicGetTemplateClient client = SEARCH_MARKET_ETP.bind(environment);
        client.getUriComponentsBuilder().queryParam(SYMBOL, marketId.getSymbol());
        SpotRealTimeNavResponse response = client.getForObject(SpotRealTimeNavResponse.class);
        if (response != null && response.getTick() != null) {
            return response.getTick();
        }
        return new SpotRealTimeNavVo();
    }

    private final PrivateGetTemplate SEARCH_ACCOUNT_INFO = PrivateGetTemplate.
            of("/v1/account/accounts", 100, Duration.ofSeconds(2L));

    @Override
    public List<SpotAccountsVo> searchAccountInfo(Credentials credentials) {
        PrivateGetTemplateClient client = SEARCH_ACCOUNT_INFO.bind(environment, credentials);
        SpotAccountsResponse response = client.getForObject(SpotAccountsResponse.class);
        if (response != null && response.getData() != null) {
            return response.getData();
        }
        return Collections.emptyList();
    }

    private final PrivateGetTemplate SEARCH_ACCOUNT_BALANCE = PrivateGetTemplate.
            of("/v1/account/accounts/{account-id}/balance", 100, Duration.ofSeconds(2L));

    @Override
    public SpotAccountBalanceVo searchAccountBalance(Credentials credentials, long accountId) {
        PrivateGetTemplateClient client = SEARCH_ACCOUNT_BALANCE.bind(environment, credentials);
        SpotAccountBalanceResponse response = client.getForObject(SpotAccountBalanceResponse.class, accountId);
        if (response != null && response.getData() != null) {
            return response.getData();
        }
        return new SpotAccountBalanceVo();
    }

    private final PrivateGetTemplate SEARCH_ACCOUNT_ASSET_VALUATION = PrivateGetTemplate.
            of("/v2/account/asset-valuation", 100, Duration.ofSeconds(2L));

    @Override
    public SpotAccountAssetValuationVo searchAccountAssetValuation(Credentials credentials, SpotAccountType type,
                                                                   @Nullable FiatCoin valuationCurrency, @Nullable String subUid) {
        PrivateGetTemplateClient client = SEARCH_ACCOUNT_ASSET_VALUATION.bind(environment, credentials);

        client.getUriComponentsBuilder().queryParam("accountType", type.getType());
        if (valuationCurrency != null)
            client.getUriComponentsBuilder().queryParam("valuationCurrency", valuationCurrency.getSymbol());

        if (subUid != null && !Objects.equals(subUid, ""))
            client.getUriComponentsBuilder().queryParam("subUid", subUid);

        SpotAccountAssetValuationResponse response = client.getForObject(SpotAccountAssetValuationResponse.class);

        if (response != null && response.getData() != null) {
            return response.getData();
        }
        return new SpotAccountAssetValuationVo();
    }

    private final PrivatePostTemplate ACCOUNT_TRANSFER = PrivatePostTemplate.
            of("/v1/account/transfer", 10, Duration.ofSeconds(1L));

    @Override
    public SpotAccountTransferVo accountTransfer(Credentials credentials, SpotAccountTransferRequest request) {
        PrivatePostTemplateClient client = ACCOUNT_TRANSFER.bind(environment, credentials);
        SpotAccountTransferResponse response = client.postForObject(request, SpotAccountTransferResponse.class);
        if (response != null && response.getData() != null) {
            return response.getData();
        }
        return new SpotAccountTransferVo();
    }

    private final PrivateGetTemplate SEARCH_ACCOUNT_HISTORY = PrivateGetTemplate.
            of("/v1/account/history", 5, Duration.ofSeconds(2L));

    @Override
    public SpotAccountHistoryResponse searchAccountHistory(Credentials credentials, long accountId, SpotMarketId marketId,
                                                           List<SpotTransactType> types, SpotApiWindow spotApiWindow) {
        PrivateGetTemplateClient client = SEARCH_ACCOUNT_HISTORY.bind(environment, credentials);

        client.getUriComponentsBuilder().queryParam("account-id", accountId);

        if (marketId != null) client.getUriComponentsBuilder().queryParam("currency", marketId.getSymbol());

        StringBuilder transactTypes = new StringBuilder();
        if (types != null) {
            for (SpotTransactType type : types) {
                transactTypes.append(",").append(type.getType());
            }
            client.getUriComponentsBuilder().queryParam("transact-types", transactTypes.deleteCharAt(0).toString());
        }
        return client.getForObject(SpotAccountHistoryResponse.class);
    }

    private final PrivateGetTemplate SEARCH_ACCOUNT_LEDGER = PrivateGetTemplate.
            of("/v2/account/ledger", 10, Duration.ofSeconds(1L));

    @Override
    public SpotAccountLedgerResponse searchAccountLedger(Credentials credentials, long accountId, SpotMarketId marketId,
                                                         List<SpotTransactType> types, SpotApiWindow spotApiWindow) {
        PrivateGetTemplateClient client = SEARCH_ACCOUNT_LEDGER.bind(environment, credentials);

        client.getUriComponentsBuilder().queryParam("account-id", accountId);

        if (marketId != null) client.getUriComponentsBuilder().queryParam("currency", marketId.getSymbol());

        StringBuilder transactTypes = new StringBuilder();
        if (types != null) {
            for (SpotTransactType type : types) {
                transactTypes.append(",").append(type.getType());
            }
            client.getUriComponentsBuilder().queryParam("transact-types", transactTypes.deleteCharAt(0).toString());
        }
        return client.getForObject(SpotAccountLedgerResponse.class);
    }

    private final PrivatePostTemplate FUTURES_TRANSFER = PrivatePostTemplate.
            of("/v1/futures/transfer", 10, Duration.ofSeconds(1L));

    @Override
    public SpotFuturesTransferResponse futuresTransfer(Credentials credentials, SpotFuturesTransferRequest request) {
        PrivatePostTemplateClient client = FUTURES_TRANSFER.bind(environment, credentials);
        SpotFuturesTransferResponse response = client.postForObject(request, SpotFuturesTransferResponse.class);
        if (response != null) {
            return response;
        }
        return new SpotFuturesTransferResponse();
    }

    private final PrivateGetTemplate SEARCH_POINT_ACCOUNT = PrivateGetTemplate.
            of("/v2/point/account", 2, Duration.ofSeconds(1L));

    @Override
    public SpotPointAccountVo searchPointAccount(Credentials credentials, Long subUserId) {
        PrivateGetTemplateClient client = SEARCH_POINT_ACCOUNT.bind(environment, credentials);
        if (subUserId != null) client.getUriComponentsBuilder().queryParam("subUid", subUserId);
        SpotPointAccountResponse response = client.getForObject(SpotPointAccountResponse.class);
        if (response != null && response.getData() != null) {
            return response.getData();
        }
        return new SpotPointAccountVo();
    }

    private final PrivatePostTemplate POINT_TRANSFER = PrivatePostTemplate.
            of("/v2/point/transfer", 2, Duration.ofSeconds(1L));

    @Override
    public SpotPointTransferVo pointTransfer(Credentials credentials, SpotPointTransferRequest request) {
        PrivatePostTemplateClient client = POINT_TRANSFER.bind(environment, credentials);
        SpotPointTransferResponse response = client.postForObject(request, SpotPointTransferResponse.class);
        if (response != null && response.getData() != null) {
            return response.getData();
        }
        return new SpotPointTransferVo();
    }

    /**
     * 判断列表窗口是否为空
     *
     * @param spotApiWindow 窗口信息
     * @param client        请求模板
     */
    private void checkOkexApiWindow(SpotApiWindow spotApiWindow, AbstractTemplateClient client) {
        if (spotApiWindow != null) {
            if (spotApiWindow.getEndTime() != null && spotApiWindow.getEndTime() != 0) {
                client.getUriComponentsBuilder().queryParam("end-time", spotApiWindow.getEndTime());
            }
            if (spotApiWindow.getStartTime() != null && spotApiWindow.getStartTime() != 0) {
                client.getUriComponentsBuilder().queryParam("start-time", spotApiWindow.getStartTime());
            }
            if (spotApiWindow.getFromId() != null && spotApiWindow.getFromId() != 0) {
                client.getUriComponentsBuilder().queryParam("from-id", spotApiWindow.getFromId());
            }
            if (spotApiWindow.getSize() != null) {
                client.getUriComponentsBuilder().queryParam("size", spotApiWindow.getSize());
            }
            if (spotApiWindow.getSort() != null) {
                client.getUriComponentsBuilder().queryParam("sort", spotApiWindow.getSort());
            }
        }
    }


}
