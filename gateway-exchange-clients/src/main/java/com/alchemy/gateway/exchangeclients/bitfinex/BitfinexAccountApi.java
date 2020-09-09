package com.alchemy.gateway.exchangeclients.bitfinex;

import com.alchemy.gateway.core.common.Credentials;
import com.alchemy.gateway.core.common.Market;
import com.alchemy.gateway.core.wallet.*;
import com.alchemy.gateway.exchangeclients.bitfinex.entity.BitfinexWallet;
import com.alchemy.gateway.exchangeclients.bitfinex.entity.Movement;
import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.ArrayType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Slf4j
public class BitfinexAccountApi implements AccountApi {

    private final BitfinexExchangeApi exchangeApi;

    public BitfinexAccountApi(BitfinexExchangeApi exchangeApi) {
        this.exchangeApi = exchangeApi;
    }

    @Override
    public List<AccountAssetResp> findUserAsset(Credentials credentials) {
        RestTemplate restTemplate = exchangeApi.getRestTemplate();

        String requestPath = "v2/auth/r/wallets";
        HttpHeaders headers = BiffinexFeatures.getHeadersToApiKey(credentials, requestPath, "");
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        String url = exchangeApi.getConnectionInfo().getRestfulApiEndpoint() + requestPath;

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);


        List<AccountAssetResp> assetRespList = new ArrayList<>();
        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            Objects.requireNonNull(responseEntity.getBody());

            JSONArray array = JSONArray.parseArray(responseEntity.getBody());

            for (int i = 0; i < array.size(); i++) {
                BitfinexWallet wallet = jsonArrayToWallet(array.getJSONArray(i));
                //目前只处理 现货。。。
                if (wallet.getWalletType().equals(BitfinexWallet.Type.EXCHANGE)) {
                    AccountAssetResp assetResp = new AccountAssetResp();
                    assetResp.setBalance(wallet.getBalance());
                    String coin=wallet.getCurrency();
                    if (coin.equalsIgnoreCase(BiffinexFeatures.USD))
                    {
                        coin=BiffinexFeatures.USDT;
                    }
                    assetResp.setCoin(coin);

                    //todo :  BitfinexWallet 没有这个值？
                    assetResp.setFrozen(wallet.getBalanceAvailable() == null ? BigDecimal.ZERO : wallet.getBalanceAvailable());
                    assetRespList.add(assetResp);

                }
            }

        } else {
            throw new IllegalStateException(requestPath + "  bitfinex 请求失败,statusCode:" + responseEntity.getStatusCode());
        }
        return assetRespList;
    }

    @Override
    public Boolean findUserStatus(Credentials credentials) {
        //todo:待完善
        return true;
    }

    @Override
    public AssetTransferResult findAssetTransfers(Credentials credentials, CursorVo cursorVo, List<Market> markets) {
        AssetTransferResult result= new AssetTransferResult();
        List<AssetTransferVo> list=new ArrayList<>();
        result.setList(list);
        return  result;
    }



    @Override
    public DepositWithdrawResult findDepositWithdraws(Credentials credentials, CursorVo cursorVo) {

        String requestPath="v2/auth/r/movements/hist";
        RestTemplate restTemplate = exchangeApi.getRestTemplate();
        HttpHeaders headers = BiffinexFeatures.getHeadersToApiKey(credentials, requestPath, "");
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        String url = exchangeApi.getConnectionInfo().getRestfulApiEndpoint() + requestPath;

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        log.info("rest 返回数据:{}",  responseEntity.getBody());

        ObjectMapper mapper = new ObjectMapper();
        ArrayType constructCollectionType = mapper.getTypeFactory().constructArrayType(Movement.class);

        DepositWithdrawResult result=new DepositWithdrawResult();

        List<DepositWithdrawVo> list=new ArrayList<>();

        int recordId=0;
        try {
            Movement[] movements = mapper.readValue(Objects.requireNonNull(responseEntity.getBody()), constructCollectionType);
            for (Movement mov:movements)
            {

                log.info("bitfinex get findDepositWithdraws  mov={}",mov);

                DepositWithdrawVo vo=new DepositWithdrawVo();
                recordId=Integer.parseInt(mov.getId());

                if (cursorVo!=null && cursorVo.getRecordId()!=null && recordId<=Integer.parseInt(cursorVo.getRecordId()))
                {
                    break;
                }
                vo.setExchangeRecordId(mov.getId());
                vo.setType(mov.getAmount().compareTo(BigDecimal.ZERO)>0?DepositWithdrawType.DEPOSIT:DepositWithdrawType.WITHDRAW);
                vo.setCoin(mov.getCurency());
                vo.setAmount(mov.getAmount());
                vo.setFee(mov.getFees());

                DepositWithdrawState state=DepositWithdrawState.BEING;
                if ("COMPLETED".equalsIgnoreCase(mov.getStatus()))
                {
                    state=DepositWithdrawState.FINISH;
                }else if ("CANCELED".equalsIgnoreCase(mov.getStatus()))
                {
                    state=DepositWithdrawState.FAILED;
                }
                vo.setState(state);

                Instant instant = mov.getMtsStarted().toInstant();
                ZoneId zoneId = ZoneId.systemDefault();
                LocalDateTime createdAt = instant.atZone(zoneId).toLocalDateTime();
                vo.setCreatedAt(createdAt);

                list.add(vo);

            }

        } catch (JsonProcessingException e) {
            throw new IllegalStateException(requestPath + "  bitfinex findDepositWithdraws 发生异常:" + e.toString());
        }

        result.setList(list);
        result.setCursorVo(CursorVo.builder().recordId(""+recordId).build());
        return result;
    }


    private BitfinexWallet jsonArrayToWallet(final JSONArray json) {
        final String walletType = json.getString(0);
        final String currency = json.getString(1);
        final BigDecimal balance = json.getBigDecimal(2);
        final BigDecimal unsettledInterest = json.getBigDecimal(3);
        final BigDecimal balanceAvailable = json.getBigDecimal(4);

        return new BitfinexWallet(walletType, currency, balance, unsettledInterest, balanceAvailable);
    }

}
