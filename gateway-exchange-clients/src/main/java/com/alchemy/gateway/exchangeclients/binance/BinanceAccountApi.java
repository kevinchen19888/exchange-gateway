package com.alchemy.gateway.exchangeclients.binance;

import com.alchemy.gateway.core.GatewayException;
import com.alchemy.gateway.core.common.Credentials;
import com.alchemy.gateway.core.common.Market;
import com.alchemy.gateway.core.common.RateLimiterManager;
import com.alchemy.gateway.core.utils.DateUtils;
import com.alchemy.gateway.core.wallet.*;
import com.alchemy.gateway.exchangeclients.common.HmacSHA256Signer;
import com.alchemy.gateway.exchangeclients.common.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author kevin chen
 */
@Slf4j
public class BinanceAccountApi implements AccountApi {

    private final BinanceExchangeApi exchangeApi;

    public BinanceAccountApi(BinanceExchangeApi exchangeApi) {
        this.exchangeApi = exchangeApi;
    }

    @Override
    public List<AccountAssetResp> findUserAsset(Credentials credentials) {

        exchangeApi.getRateLimiterManager()
                .getRateLimiter(exchangeApi.getName() + "findUserAsset" + credentials.getApiKey(), 4)
                .acquire();

        ResponseEntity<String> exchangeResp = getResponseEntityWithOutStartTime(credentials, "/api/v3/account");

        List<AccountAssetResp> respList = new ArrayList<>();
        if (HttpUtil.isSuccessResp(exchangeResp)) {
            JSONObject jsonObject = JSON.parseObject(exchangeResp.getBody());
            JSONArray balances = jsonObject.getJSONArray("balances");
            if (balances != null) {
                for (int i = 0; i < balances.size(); i++) {
                    AccountAssetResp resp = new AccountAssetResp();
                    resp.setCoin(balances.getJSONObject(i).getString("asset"));
                    resp.setBalance(balances.getJSONObject(i).getBigDecimal("free").setScale(8, BigDecimal.ROUND_HALF_DOWN));
                    resp.setFrozen(balances.getJSONObject(i).getBigDecimal("locked").setScale(8, BigDecimal.ROUND_HALF_DOWN));
                    respList.add(resp);
                }

                return respList;
            }
        } else {
            log.warn("获取binance账户下所有资产失败-apiKey:{},statusCode:{}", credentials.getApiKey(), exchangeResp.getStatusCode());
            throw new GatewayException("findUserAsset请求失败,statusCode:" + exchangeResp.getStatusCode());
        }
        return respList;
    }

    @Override
    public Boolean findUserStatus(Credentials credentials) {

        rateLimit(exchangeApi.getName() + "findUserStatus" + credentials.getApiKey());

        ResponseEntity<String> userStatusResp = getResponseEntityWithOutStartTime(credentials, "/wapi/v3/accountStatus.html");
        if (HttpUtil.isSuccessResp(userStatusResp)) {
            JSONObject jsonResp = JSON.parseObject(userStatusResp.getBody());
            log.info("findUserStatus返回:{}", jsonResp);
            return "Normal".equalsIgnoreCase(jsonResp.getString("msg"));
        } else {
            log.warn("获取binance账户是否正常的状态失败-apiKey:{},statusCode:{}", credentials.getApiKey(), userStatusResp.getStatusCode());
            throw new GatewayException("findUserStatus请求失败,statusCode:" + userStatusResp.getStatusCode());
        }
    }

    @Override
    public AssetTransferResult findAssetTransfers(Credentials credentials, CursorVo cursorVo, List<Market> markets) {
        // binance 暂不支持获取资产划转记录
        return null;
    }

    @Override
    public DepositWithdrawResult findDepositWithdraws(Credentials credentials, CursorVo assetCursorVo) {
        rateLimit(exchangeApi.getName() + "findDepositWithdraws" + credentials.getApiKey());

        ResponseEntity<String> chargeResp = getResponseEntityWithStartTime(credentials, assetCursorVo.getTime(), "/sapi/v1/capital/deposit/hisrec");
        ResponseEntity<String> withdrawResp = getResponseEntityWithStartTime(credentials, assetCursorVo.getTime(), "/sapi/v1/capital/withdraw/history");

        if (HttpUtil.isSuccessResp(chargeResp) && HttpUtil.isSuccessResp(withdrawResp)) {
            DepositWithdrawResult result = new DepositWithdrawResult();

            List<DepositWithdrawVo> voList = new ArrayList<>();
            JSONArray chargeRecords = JSON.parseArray(chargeResp.getBody());
            Long timestamp = null;
            if (!CollectionUtils.isEmpty(chargeRecords)) {
                JSONObject latestCharge = chargeRecords.getJSONObject(0);// 最近一条数据
                DepositWithdrawVo deposit = new DepositWithdrawVo();
                deposit.setCoin(latestCharge.getString("coin"));
                deposit.setAmount(latestCharge.getBigDecimal("amount"));
                deposit.setExchangeRecordId(latestCharge.getString("txId"));
                deposit.setType(DepositWithdrawType.DEPOSIT);
                deposit.setCreatedAt(DateUtils.getEpochMilliByTime(latestCharge.getLong("insertTime")));
                // set default value
                deposit.setFee(BigDecimal.ZERO);
                timestamp = latestCharge.getLong("insertTime");

                String status = latestCharge.getString("status");
                deposit.setState("1".equals(status) ? DepositWithdrawState.FINISH : "0".equals(status) ?
                        DepositWithdrawState.BEING : DepositWithdrawState.FAILED);
                voList.add(deposit);
            }
            JSONArray withdrawRecords = JSON.parseArray(withdrawResp.getBody());
            if (!CollectionUtils.isEmpty(withdrawRecords)) {
                JSONObject latestWithdraw = withdrawRecords.getJSONObject(0);
                DepositWithdrawVo withdraw = new DepositWithdrawVo();
                withdraw.setType(DepositWithdrawType.WITHDRAW);
                Long applyTime = DateUtils.getEpochMillIByTimeStr(latestWithdraw.getString("applyTime"));
                withdraw.setCreatedAt(DateUtils.getStringByLocalDateTime(latestWithdraw.getString("applyTime")));
                if (timestamp == null) {
                    timestamp = applyTime;
                } else {
                    assert applyTime != null;
                    timestamp = timestamp.compareTo(applyTime) > 0 ? timestamp : applyTime;
                }

                withdraw.setCoin(latestWithdraw.getString("coin"));
                withdraw.setExchangeRecordId(latestWithdraw.getString("txId"));
                withdraw.setAmount(latestWithdraw.getBigDecimal("amount"));
                withdraw.setFee(BigDecimal.ZERO);
                String status = latestWithdraw.getString("status");
                DepositWithdrawState state = null;
                switch (status) {
                    case "0":
                    case "2":
                    case "4":
                        state = DepositWithdrawState.BEING;
                        break;
                    case "1":
                    case "3":
                    case "5":
                        state = DepositWithdrawState.FAILED;
                        break;
                    case "6":
                        state = DepositWithdrawState.FINISH;
                        break;
                    default:
                        break;
                }
                withdraw.setState(state);
                voList.add(withdraw);
            }
            // 排除游标数据
            if (assetCursorVo.getTime() != null) {
                voList = voList.stream()
                        .filter(v -> v.getCreatedAt().compareTo(DateUtils.getEpochMilliByTime(assetCursorVo.getTime()))>0)
                        .collect(Collectors.toList());
            }
            assetCursorVo.setTime(timestamp);
            result.setList(voList);
            result.setCursorVo(assetCursorVo);
            return result;
        } else {
            HttpStatus httpStatus = HttpUtil.isSuccessResp(chargeResp) ? withdrawResp.getStatusCode() : chargeResp.getStatusCode();
            log.warn("获取用户充提币记录请求错误-apiKey:{},statusCode:{}", credentials.getApiKey(), httpStatus);
            throw new GatewayException("获取用户充提币记录请求错误,httpStatus:" + httpStatus);
        }
    }

    private HttpEntity<MultiValueMap<String, Object>> getHttpEntity(Credentials credentials) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-MBX-APIKEY", credentials.getApiKey());
        return new HttpEntity<>(headers);
    }

    private ResponseEntity<String> getResponseEntityWithStartTime(Credentials credentials, Long startTime, String uri) {
        RestTemplate restTemplate = exchangeApi.getRestTemplate();

        HttpEntity<MultiValueMap<String, Object>> httpEntity = getHttpEntity(credentials);

        Map<String, Object> params = new HashMap<>();
        long timeStamp = System.currentTimeMillis();
        params.put("timestamp", timeStamp);
        params.put("recvWindow", exchangeApi.recvWindow);
        if (startTime != null) {
            params.put("startTime", startTime);
        }
        String signature = HmacSHA256Signer.sign(credentials.getSecretKey(), params);

        final StringBuilder url = new StringBuilder(exchangeApi.getConnectionInfo().getRestfulApiEndpoint() + uri + "?"
                + "&recvWindow=" + exchangeApi.recvWindow);

        if (startTime != null) {
            url.append("&startTime=").append(startTime);
        }
        url.append("&timestamp=").append(timeStamp).append("&signature=").append(signature);

        return restTemplate.exchange(url.toString(), HttpMethod.GET, httpEntity, String.class);
    }

    private ResponseEntity<String> getResponseEntityWithOutStartTime(Credentials credentials, String uri) {
        RestTemplate restTemplate = exchangeApi.getRestTemplate();
        Map<String, Object> params = new HashMap<>();
        long timeStamp = System.currentTimeMillis();
        params.put("timestamp", timeStamp);
        params.put("recvWindow", exchangeApi.recvWindow);
        String signature = HmacSHA256Signer.sign(credentials.getSecretKey(), params);

        HttpEntity<MultiValueMap<String, Object>> httpEntity = getHttpEntity(credentials);

        String url = exchangeApi.getConnectionInfo().getRestfulApiEndpoint()
                + uri + "?recvWindow=" + exchangeApi.recvWindow + "&timestamp=" + timeStamp + "&signature=" + signature;

        return restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
    }

    private void rateLimit(String key) {
        RateLimiterManager limiterManager = exchangeApi.getRateLimiterManager();
        RateLimiter limiter = limiterManager.getRateLimiter(key, 10);

        limiter.acquire();
    }
}
