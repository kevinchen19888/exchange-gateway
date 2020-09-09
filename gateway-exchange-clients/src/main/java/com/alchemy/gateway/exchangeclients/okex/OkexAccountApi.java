package com.alchemy.gateway.exchangeclients.okex;

import com.alchemy.gateway.core.common.Credentials;
import com.alchemy.gateway.core.common.Market;
import com.alchemy.gateway.core.common.RateLimiterManager;
import com.alchemy.gateway.core.utils.DateUtils;
import com.alchemy.gateway.core.wallet.*;
import com.alchemy.gateway.exchangeclients.okex.impl.OkexFeatures;
import com.alchemy.gateway.exchangeclients.okex.resultModel.Account;
import com.alchemy.gateway.exchangeclients.okex.resultModel.AssetTransferLedger;
import com.alchemy.gateway.exchangeclients.okex.resultModel.Ledger;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
public class OkexAccountApi implements AccountApi {

    private final OkexExchangeApi exchangeApi;

    public OkexAccountApi(OkexExchangeApi exchangeApi) {
        this.exchangeApi = exchangeApi;
    }

    @Override
    public List<AccountAssetResp> findUserAsset(Credentials credentials) {
        RestTemplate restTemplate = exchangeApi.getRestTemplate();

        String requestPath = "/api/spot/v3/accounts";

        final String key = exchangeApi.getName() + requestPath + credentials.getApiKey();
        rateLimit(key, 5.0);

        HttpHeaders headers = OkexFeatures.getHeadersToApiKey(credentials, HttpMethod.GET.name(), requestPath, "", "");
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        String url = exchangeApi.getConnectionInfo().getRestfulApiEndpoint() + requestPath;

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
        log.info("findUserAsset请求数据:{}", responseEntity.getBody());

        List<AccountAssetResp> assetRespList = new ArrayList<>();
        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            if (!Objects.requireNonNull(responseEntity.getBody()).isEmpty()) {
                List<Account> accounts = JSON.parseArray(responseEntity.getBody(), Account.class);
                accounts.forEach(item -> {
                    AccountAssetResp assetResp = new AccountAssetResp();
                    assetResp.setBalance(new BigDecimal(item.getAvailable()));
                    assetResp.setCoin(item.getCurrency());
                    assetResp.setFrozen(new BigDecimal(item.getHold()));
                    assetRespList.add(assetResp);
                });
            }
        } else {
            throw new IllegalStateException(HttpMethod.GET + url + "请求失败,statusCode:" + responseEntity.getStatusCode());
        }
        return assetRespList;
    }

    @Override
    public Boolean findUserStatus(Credentials credentials) {
        return true;
    }

    @Override
    public AssetTransferResult findAssetTransfers(Credentials credentials, CursorVo cursorVo, List<Market> markets) {
        RestTemplate restTemplate = exchangeApi.getRestTemplate();
        AssetTransferResult into = getAssetTransfers(credentials, cursorVo, restTemplate, 37);
        AssetTransferResult out = getAssetTransfers(credentials, cursorVo, restTemplate, 38);

        List<AssetTransferVo> items = new ArrayList<>();
        items.addAll(into.getList());
        items.addAll(out.getList());

        AssetTransferResult assetTransferResult = new AssetTransferResult();
        assetTransferResult.setList(items);

        long intoCount = Long.parseLong(into.getCursorVo().getRecordId());
        long outCount = Long.parseLong(out.getCursorVo().getRecordId());

        if (intoCount == 0) {
            intoCount = Long.MAX_VALUE;
        }
        if (outCount == 0) {
            outCount = Long.MAX_VALUE;
        }
        assetTransferResult.setCursorVo(CursorVo.builder().recordId(String.valueOf(Math.min(intoCount, outCount))).build());

        return assetTransferResult;
    }

    /**
     * 转入转出币币账户
     *
     * @param credentials  用户信息
     * @param cursorVo     查询参数
     * @param restTemplate api请求
     * @param type         转入37还是转出38
     * @return AssetTransferResult
     */
    private AssetTransferResult getAssetTransfers(Credentials credentials, CursorVo cursorVo, RestTemplate restTemplate, int type) {

        List<AssetTransferVo> items = new ArrayList<>();
        String recordId = "0";

        String requestPath = "/api/account/v3/ledger";

        final String key = exchangeApi.getName() + requestPath + credentials.getApiKey();
        rateLimit(key, 3.0);

        StringBuilder queryString = new StringBuilder().append("type=").append(type);
        if (cursorVo != null && !StringUtils.isEmpty(cursorVo.getRecordId())) {
            long beforeId = Long.parseLong(cursorVo.getRecordId());
            if (beforeId != 0 && beforeId != Long.MAX_VALUE) {
                queryString.append("&before=").append(beforeId);
            }
        }
        HttpHeaders headers = OkexFeatures.getHeadersToApiKey(credentials, HttpMethod.GET.name(), requestPath, queryString.toString(), "");
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        String url = exchangeApi.getConnectionInfo().getRestfulApiEndpoint() + requestPath + "?" + queryString;

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
        log.info("转入转出币币账户记录返回数据:{}", responseEntity.getBody());

        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            if (!Objects.requireNonNull(responseEntity.getBody()).isEmpty()) {
                List<AssetTransferLedger> ledgerList = JSONArray.parseArray(responseEntity.getBody(), AssetTransferLedger.class);
                if (ledgerList != null && ledgerList.size() > 0) {
                    ledgerList.forEach(ledger -> {
                        AssetTransferVo assetTransferVo = new AssetTransferVo();
                        assetTransferVo.setAmount(new BigDecimal(ledger.getAmount()));
                        assetTransferVo.setCoin(ledger.getCurrency());
                        assetTransferVo.setExchangeRecordId(String.valueOf(ledger.getLedger_id()));
                        assetTransferVo.setCreatedAt(DateUtils.getTimeByUtc(ledger.getTimestamp()));
                        assetTransferVo.setFrom(ledger.getFrom());
                        assetTransferVo.setTo(ledger.getTo());
                        assetTransferVo.setType(ledger.getTypename());
                        items.add(assetTransferVo);
                    });
                    recordId = String.valueOf(ledgerList.get(0).getLedger_id());
                }
            }
        } else {
            throw new IllegalStateException(HttpMethod.GET + url + "请求失败,statusCode:" + responseEntity.getStatusCode());
        }

        AssetTransferResult assetTransferResult = new AssetTransferResult();
        assetTransferResult.setList(items);
        assetTransferResult.setCursorVo(CursorVo.builder().recordId(recordId).build());
        return assetTransferResult;
    }

    @Override
    public DepositWithdrawResult findDepositWithdraws(Credentials credentials, CursorVo cursorVo) {
        RestTemplate restTemplate = exchangeApi.getRestTemplate();

        DepositWithdrawResult depositResult = getDepositWithdraws(credentials, cursorVo, restTemplate, 1);
        DepositWithdrawResult withdrawResult = getDepositWithdraws(credentials, cursorVo, restTemplate, 2);
        DepositWithdrawResult revocationWithdrawResult = getDepositWithdraws(credentials, cursorVo, restTemplate, 13);

        List<DepositWithdrawVo> items = new ArrayList<>();
        items.addAll(depositResult.getList());
        items.addAll(withdrawResult.getList());
        items.addAll(revocationWithdrawResult.getList());

        DepositWithdrawResult depositWithdrawResult = new DepositWithdrawResult();
        depositWithdrawResult.setList(items);

        long deposit = Long.parseLong(depositResult.getCursorVo().getRecordId());
        long withdraw = Long.parseLong(withdrawResult.getCursorVo().getRecordId());
        long revocation = Long.parseLong(revocationWithdrawResult.getCursorVo().getRecordId());

        if (deposit == 0) {
            deposit = Long.MAX_VALUE;
        }
        if (withdraw == 0) {
            withdraw = Long.MAX_VALUE;
        }
        if (revocation == 0) {
            revocation = Long.MAX_VALUE;
        }
        depositWithdrawResult.setCursorVo(CursorVo.builder().recordId(String.valueOf(Math.min(Math.min(deposit, withdraw), revocation))).build());

        return depositWithdrawResult;
    }

    /**
     * 充提币记录
     *
     * @param credentials  用户信息
     * @param cursorVo     查询参数
     * @param restTemplate api请求
     * @param type         1: 充值 2: 提现 13: 撤销提现
     * @return ResponseEntity
     */
    private DepositWithdrawResult getDepositWithdraws(Credentials credentials, CursorVo cursorVo, RestTemplate restTemplate, int type) {

        List<DepositWithdrawVo> items = new ArrayList<>();
        String recordId = "0";

        String requestPath = "/api/account/v3/ledger";

        final String key = exchangeApi.getName() + requestPath + credentials.getApiKey();
        rateLimit(key, 3.0);

        StringBuilder queryString = new StringBuilder().append("type=").append(type);
        if (cursorVo != null && !StringUtils.isEmpty(cursorVo.getRecordId())) {
            long beforeId = Long.parseLong(cursorVo.getRecordId());
            if (beforeId != 0 && beforeId != Long.MAX_VALUE) {
                queryString.append("&before=").append(beforeId);
            }
        }

        HttpHeaders headers = OkexFeatures.getHeadersToApiKey(credentials, HttpMethod.GET.name(), requestPath, queryString.toString(), "");
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        String url = exchangeApi.getConnectionInfo().getRestfulApiEndpoint() + requestPath + "?" + queryString;

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
        log.info("充提币记录返回数据:{}", responseEntity.getBody());

        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            if (!Objects.requireNonNull(responseEntity.getBody()).isEmpty()) {
                List<Ledger> ledgerList = JSONArray.parseArray(responseEntity.getBody(), Ledger.class);
                if (ledgerList != null && ledgerList.size() > 0) {
                    ledgerList.forEach(ledger -> {
                        DepositWithdrawVo depositWithdrawVo = new DepositWithdrawVo();
                        depositWithdrawVo.setAmount(new BigDecimal(ledger.getAmount()));
                        depositWithdrawVo.setCoin(ledger.getCurrency());
                        depositWithdrawVo.setExchangeRecordId(ledger.getLedger_id());
                        depositWithdrawVo.setCreatedAt(DateUtils.getTimeByUtc(ledger.getTimestamp()));
                        depositWithdrawVo.setFee(new BigDecimal(ledger.getFee()));
                        if (type == 1) {
                            depositWithdrawVo.setState(DepositWithdrawState.FINISH);
                            depositWithdrawVo.setType(DepositWithdrawType.DEPOSIT);
                        }
                        if (type == 2) {
                            depositWithdrawVo.setState(DepositWithdrawState.FINISH);
                            depositWithdrawVo.setType(DepositWithdrawType.WITHDRAW);
                        }
                        if (type == 13) {
                            depositWithdrawVo.setState(DepositWithdrawState.FAILED);
                            depositWithdrawVo.setType(DepositWithdrawType.WITHDRAW);
                        }
                        items.add(depositWithdrawVo);
                    });
                    recordId = String.valueOf(ledgerList.get(0).getLedger_id());
                }
            }
        } else {
            throw new IllegalStateException(HttpMethod.GET + url + "请求失败,statusCode:" + responseEntity.getStatusCode());
        }

        DepositWithdrawResult depositWithdrawResult = new DepositWithdrawResult();
        depositWithdrawResult.setList(items);
        depositWithdrawResult.setCursorVo(CursorVo.builder().recordId(recordId).build());
        return depositWithdrawResult;
    }

    private void rateLimit(String key, double permitsPerSecond) {
        RateLimiterManager limiterManager = exchangeApi.getRateLimiterManager();
        RateLimiter limiter = limiterManager.getRateLimiter(key, permitsPerSecond);
        limiter.acquire();
    }

}
