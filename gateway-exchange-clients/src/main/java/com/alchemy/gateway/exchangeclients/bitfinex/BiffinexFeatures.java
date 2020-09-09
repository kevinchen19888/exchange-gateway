package com.alchemy.gateway.exchangeclients.bitfinex;

import com.alchemy.gateway.core.common.CoinPair;
import com.alchemy.gateway.core.common.Credentials;
import com.alchemy.gateway.core.common.MarketType;
import com.alchemy.gateway.core.info.Features;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 */
public class BiffinexFeatures implements Features {

    public static final String USDT="USDT";
    public static final String USD="USD";

    public static final String PREFIX ="t";


    @Override
    public List<MarketType> supportMarketTypes() {
        return Arrays.asList(MarketType.SPOT, MarketType.FUTURE);
    }

    public static HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("Cookie", "locale=zh_CN");
        headers.add("Content-Type", "application/json; charset=UTF-8");
        headers.add("User-Agent", "Postman");
        return headers;
    }


    public static HttpHeaders getHeadersToApiKey(Credentials credentials, String requestPath, String body)  {
        HttpHeaders headers = getHeaders();
        if (credentials != null) {

            long timestamp =System.currentTimeMillis();
            String signature;
            signature = new String(("/api/"+requestPath+timestamp+body).getBytes(StandardCharsets.UTF_8));

            String sign = null;
            try {
                sign = CryptUtils.hmacEncode(signature, credentials.getSecretKey());
            } catch (Exception e) {
                e.printStackTrace();
            }
            headers.add("bfx-nonce", ""+timestamp );
            headers.add("bfx-apikey", credentials.getApiKey());
            headers.add("bfx-signature", sign);
        }
        return headers;
    }


    public static String convertUsd2Usdt(String s)
    {
        //去掉前面的t  ，并且把USD 转换成USDT
        if (s.startsWith(PREFIX))
        {
            s=s.substring(1);
        }
        if(!s.contains(USDT) && s.indexOf(USD)>0)
        {
            return s.replace(USD,USDT);
        }
        return s;
    }



    public static String convertUsdt2Usd(String s)
    {
        s=s.toUpperCase();
        if(s.indexOf(USDT)>0 )
        {
            return PREFIX+s.replace(USDT,USD).replace("/","");
        }
        return PREFIX+s.replace("/","");
    }

    public static String convert2Usd(CoinPair pair)
    {
        return convertUsdt2Usd(pair.toSymbol());
    }


    public static void main(String[] args) {
        convertUsdt2Usd("BTC/ETH");

    }

}
