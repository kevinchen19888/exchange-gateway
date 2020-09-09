package com.alchemy.gateway.exchangeclients.huobi;

import com.alchemy.gateway.core.AbstractExchangeApi;
import com.alchemy.gateway.core.GatewayException;
import com.alchemy.gateway.core.common.Credentials;
import com.alchemy.gateway.core.common.Market;
import com.alchemy.gateway.core.common.RateLimiterManager;
import com.alchemy.gateway.core.utils.DateUtils;
import com.alchemy.gateway.core.wallet.*;
import com.alchemy.gateway.exchangeclients.common.HttpUtil;
import com.alchemy.gateway.exchangeclients.huobi.util.HuobiApiSignature;
import com.alchemy.gateway.exchangeclients.huobi.util.UrlParamsBuilder;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.util.concurrent.RateLimiter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author kevin chen
 */
@Slf4j
public class HuobiAccountApi implements AccountApi {

    private final AbstractExchangeApi exchangeApi;

    public HuobiAccountApi(AbstractExchangeApi exchangeApi) {
        this.exchangeApi = exchangeApi;
    }

    @Override
    @SneakyThrows
    public Boolean findUserStatus(Credentials credentials) {

        RestTemplate restTemplate = exchangeApi.getRestTemplate();

        JSONObject spotAccountInfo = getSpotAccountInfo(credentials, restTemplate);

        assert spotAccountInfo != null;
        return "working".equalsIgnoreCase(spotAccountInfo.getString("state"));
    }

    @Override
    @SneakyThrows
    public List<AccountAssetResp> findUserAsset(Credentials credentials) {

        RestTemplate restTemplate = exchangeApi.getRestTemplate();

        JSONObject spotAccountInfo = getSpotAccountInfo(credentials, restTemplate);

        final String address = String.format("/v1/account/accounts/%s/balance", spotAccountInfo.getString("id"));
        final String apiEndpoint = exchangeApi.getConnectionInfo().getRestfulApiEndpoint();
        String host = new URL(apiEndpoint).getHost();
        String signedParams = HuobiApiSignature.createSignature(credentials.getApiKey(), credentials.getSecretKey(),
                HttpUtil.GET, host, address, UrlParamsBuilder.build()).buildUrl();

        HttpEntity<String> httpEntity = getHttpEntity();

        // 设置已转义字符禁止再次转义
        URI uri = UriComponentsBuilder.fromHttpUrl(apiEndpoint + address + signedParams).build(true).toUri();

        ResponseEntity<String> exchangeResp = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, String.class);

        if (HttpUtil.isSuccessResp(exchangeResp)) {
            JSONObject respBody = JSON.parseObject(exchangeResp.getBody());
            if ("error".equals(respBody.getString("status"))) {
                log.error("huobi findUserAsset error,err-code:{},err-msg:{}", respBody.getString("err-code"), respBody.getString("err-msg"));
                return new ArrayList<>();
            }

            JSONArray assets = respBody.getJSONObject("data").getJSONArray("list");
            List<AccountAssetResp> assetResps = new ArrayList<>();
            if (assets == null) {
                return assetResps;
            }
            Map<String, List<Map>> currencies = assets.toJavaList(Map.class).stream()
                    .collect(Collectors.groupingBy(map -> (String) map.get("currency")));

            for (Map.Entry<String, List<Map>> entry : currencies.entrySet()) {
                AccountAssetResp assetResp = new AccountAssetResp();
                assetResp.setCoin(entry.getKey());
                for (Map map : entry.getValue()) {
                    BigDecimal balance = new BigDecimal((String) map.get("balance")).setScale(8, BigDecimal.ROUND_HALF_DOWN);
                    if ("trade".equals(map.get("type"))) {
                        assetResp.setBalance(balance);
                    }
                    if ("frozen".equals(map.get("type"))) {
                        assetResp.setFrozen(balance);
                    }
                }
                assetResps.add(assetResp);
            }

            return assetResps;
        } else {
            throw new GatewayException("获取huobi账户资产信息请求异常,statusCode:" + exchangeResp.getStatusCode());
        }
    }

    @SneakyThrows
    @Override
    public AssetTransferResult findAssetTransfers(Credentials credentials, CursorVo cursorVo, List<Market> markets) {

        String apiEndpoint = exchangeApi.getConnectionInfo().getRestfulApiEndpoint();

        RestTemplate restTemplate = exchangeApi.getRestTemplate();

        JSONObject accountInfo = getSpotAccountInfo(credentials, restTemplate);

        UrlParamsBuilder builder = UrlParamsBuilder.build()
                .putToUrl("account-id", accountInfo.getString("id"))
                .putToUrl("transact-types", "transfer")
                .putToUrl("sort", "desc");
        if (cursorVo != null && cursorVo.getTime() != null) {
            builder.putToUrl("start-time", cursorVo.getTime());
        }
        String signedParams = HuobiApiSignature.createSignature(credentials.getApiKey(), credentials.getSecretKey(),
                HttpUtil.GET, new URL(apiEndpoint).getHost(), "/v1/account/history", builder).buildUrl();

        HttpEntity<String> httpEntity = getHttpEntity();

        URI uri = UriComponentsBuilder.fromHttpUrl(apiEndpoint + "/v1/account/history" + signedParams).build(true).toUri();

        ResponseEntity<String> exchangeResp = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, String.class);

        if (HttpUtil.isSuccessResp(exchangeResp)) {
            JSONObject respBody = JSON.parseObject(exchangeResp.getBody());

            assertOkResp(respBody, "huobi findAssetTransfers error");

            JSONArray data = respBody.getJSONArray("data");
            AssetTransferResult result = new AssetTransferResult();
            List<AssetTransferVo> transferList = new ArrayList<>();
            if (data != null) {
                for (int i = 0; i < data.size(); i++) {
                    AssetTransferVo vo = new AssetTransferVo();
                    JSONObject flow = data.getJSONObject(i);
                    vo.setAmount(flow.getBigDecimal("transact-amt"));
                    vo.setCoin(flow.getString("currency"));
                    vo.setCreatedAt(DateUtils.getEpochMilliByTime(flow.getLong("transact-time")));
                    vo.setType(flow.getString("transact-type"));
                    vo.setExchangeRecordId(flow.getString("record-id"));
                    transferList.add(vo);
                }
                // 记录游标
                if (data.size() > 0) {
                    Long latestTime = data.getJSONObject(0).getLong("transact-time");
                    // 排除游标数据
                    if (cursorVo != null && cursorVo.getTime() != null) {
                        if (cursorVo.getTime().equals(latestTime)) {
                            return result;
                        }
                    }
                    if (cursorVo == null) {
                        cursorVo = CursorVo.builder().build();
                    }
                    cursorVo.setTime(latestTime);
                }
            }
            result.setList(transferList);
            result.setCursorVo(cursorVo);
            return result;
        } else {
            throw new GatewayException("获取huobi资产划转记录请求失败,statusCode:" + exchangeResp.getStatusCode());
        }
    }

    @Override
    @SneakyThrows
    public DepositWithdrawResult findDepositWithdraws(Credentials credentials, CursorVo cursorVo) {

        String fromId = cursorVo != null && StringUtils.hasText(cursorVo.getRecordId()) ? cursorVo.getRecordId() : null;

        ResponseEntity<String> depositResp = getDepositWithdraws(credentials, "deposit", fromId);
        ResponseEntity<String> withdrawResp = getDepositWithdraws(credentials, "withdraw", fromId);

        if (HttpUtil.isSuccessResp(depositResp) && HttpUtil.isSuccessResp(withdrawResp)) {
            DepositWithdrawResult result = new DepositWithdrawResult();
            JSONArray depositData = JSON.parseObject(depositResp.getBody()).getJSONArray("data");
            JSONArray withdrawData = JSON.parseObject(withdrawResp.getBody()).getJSONArray("data");
            JSONArray resp = new JSONArray();
            if (depositData != null) {
                resp.addAll(depositData);
            }
            if (withdrawData != null) {
                resp.addAll(withdrawData);
            }

            if (CollectionUtils.isEmpty(resp)) {
                return result;
            }

            List<DepositWithdrawVo> voList = new ArrayList<>();
            for (int i = 0; i < resp.size(); i++) {
                DepositWithdrawVo vo = new DepositWithdrawVo();
                JSONObject record = resp.getJSONObject(i);
                vo.setExchangeRecordId(record.getString("id"));
                if ("deposit".equals(record.getString("type"))) {
                    vo.setType(DepositWithdrawType.DEPOSIT);
                } else {
                    vo.setType(DepositWithdrawType.WITHDRAW);
                }
                vo.setAmount(record.getBigDecimal("amount"));
                vo.setCoin(record.getString("currency"));
                vo.setCreatedAt(DateUtils.getEpochMilliByTime(record.getLong("created-at")));
                vo.setFee(record.getBigDecimal("fee"));
                switch (record.getString("state")) {
                    case "safe":
                        vo.setState(DepositWithdrawState.FINISH);
                        break;
                    case "orphan":
                    case "confirming":
                    case "confirmed":
                        vo.setState(DepositWithdrawState.BEING);
                        break;
                    case "unknown":
                        vo.setState(DepositWithdrawState.FAILED);
                        break;
                    default:
                        break;
                }
                voList.add(vo);
            }

            Optional<Long> maxId = voList.stream().map(vo -> Long.parseLong(vo.getExchangeRecordId())).max(Comparator.naturalOrder());
            if (maxId.isPresent()) {
                // 排除游标数据
                if (fromId != null && fromId.equals(String.valueOf(maxId.get()))) {
                    return result;
                }
                CursorVo cursor = CursorVo.builder().recordId(String.valueOf(maxId.get())).build();
                result.setCursorVo(cursor);
            }
            result.setList(voList);
            return result;
        } else {
            throw new GatewayException("获取huobi充提币记录请求失败,statusCode:"
                    + (HttpUtil.isSuccessResp(depositResp) ? withdrawResp.getStatusCode() : depositResp.getStatusCode()));
        }
    }

    private ResponseEntity<String> getDepositWithdraws(Credentials credentials, String type, String fromId) throws MalformedURLException {

        rateLimit(exchangeApi.getName() + "getDepositWithdraws" + credentials.getApiKey());

        RestTemplate restTemplate = exchangeApi.getRestTemplate();

        UrlParamsBuilder builder = UrlParamsBuilder.build().putToUrl("type", type);
        if (fromId != null) {
            builder.putToUrl("from", fromId);
        }

        final String apiEndpoint = exchangeApi.getConnectionInfo().getRestfulApiEndpoint();
        String host = new URL(apiEndpoint).getHost();

        String signedParams = HuobiApiSignature.createSignature(credentials.getApiKey(), credentials.getSecretKey(),
                HttpUtil.GET, host, "/v1/query/deposit-withdraw", builder).buildUrl();

        URI uri = UriComponentsBuilder.fromHttpUrl(apiEndpoint + "/v1/query/deposit-withdraw" + signedParams).build(true).toUri();

        HttpEntity<String> httpEntity = getHttpEntity();

        return restTemplate.exchange(uri, HttpMethod.GET, httpEntity, String.class);
    }

    public JSONObject getSpotAccountInfo(Credentials credentials, RestTemplate restTemplate) throws MalformedURLException {
        exchangeApi.getRateLimiterManager()
                .getRateLimiter(exchangeApi.getName() + "getSpotAccountInfo" + credentials.getApiKey(), 50)
                .acquire();

        final String apiEndpoint = exchangeApi.getConnectionInfo().getRestfulApiEndpoint();
        String host = new URL(apiEndpoint).getHost();
        String signedParams = HuobiApiSignature.createSignature(credentials.getApiKey(), credentials.getSecretKey(),
                HttpUtil.GET, host, "/v1/account/accounts", UrlParamsBuilder.build()).buildUrl();

        HttpEntity<String> httpEntity = getHttpEntity();
        // 设置转义字符禁止转义
        URI uri = UriComponentsBuilder.fromHttpUrl(apiEndpoint + "/v1/account/accounts" + signedParams).build(true).toUri();

        ResponseEntity<String> exchangeResp = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, String.class);

        if (HttpUtil.isSuccessResp(exchangeResp)) {
            JSONObject respBody = JSONObject.parseObject(exchangeResp.getBody());

            assertOkResp(respBody,"huobi getSpotAccountInfo error");

            JSONArray data = respBody.getJSONArray("data");
            if (data != null) {
                for (int i = 0; i < data.size(); i++) {
                    if ("spot".equals(data.getJSONObject(i).getString("type"))) {
                        return data.getJSONObject(i);
                    }
                }
            }
        } else {
            throw new GatewayException("获取huobi现货账户信息异常,statusCode:" + exchangeResp.getStatusCode());
        }
        return new JSONObject();
    }

    private HttpEntity<String> getHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        headers.add("user-agent", HttpUtil.USER_AGENT);
        return new HttpEntity<>(headers);
    }

    private void rateLimit(String key) {
        RateLimiterManager limiterManager = exchangeApi.getRateLimiterManager();
        RateLimiter limiter = limiterManager.getRateLimiter(key, 10);

        limiter.acquire();
    }

    private void assertOkResp(JSONObject respBody, String source) {
        if ("error".equals(respBody.getString("status"))) {
            throw new GatewayException(source + ",errCode:"
                    + respBody.getString("err-code") + ",errMsg:" + respBody.getString("err-msg"));
        }
    }
}
