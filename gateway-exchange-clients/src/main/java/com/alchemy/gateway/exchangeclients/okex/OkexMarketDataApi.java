package com.alchemy.gateway.exchangeclients.okex;

import com.alchemy.gateway.core.common.CandleInterval;
import com.alchemy.gateway.core.common.CoinPairSymbolConverter;
import com.alchemy.gateway.core.common.Market;
import com.alchemy.gateway.core.marketdata.CandleTick;
import com.alchemy.gateway.core.marketdata.DataSubscription;
import com.alchemy.gateway.core.marketdata.MarketDataApi;
import com.alchemy.gateway.core.marketdata.MarketDataReceiver;
import com.alchemy.gateway.core.utils.DateUtils;
import com.alchemy.gateway.core.websocket.BinaryMessageCompression;
import com.alchemy.gateway.exchangeclients.okex.impl.OkexBinaryMessageCompression;
import com.alchemy.gateway.exchangeclients.okex.impl.OkexCoinPairSymbolConverter;
import com.alchemy.gateway.exchangeclients.okex.impl.OkexFeatures;
import com.alibaba.fastjson.JSONArray;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.alchemy.gateway.core.common.CandleInterval.*;

@Slf4j
public class OkexMarketDataApi implements MarketDataApi {

    private final CoinPairSymbolConverter coinPairSymbolConverter;
    private final OkexExchangeApi exchangeApi;
    private final BinaryMessageCompression binaryMessageCompression;

    public OkexMarketDataApi(OkexExchangeApi exchangeApi) {
        this.coinPairSymbolConverter = new OkexCoinPairSymbolConverter();
        this.exchangeApi = exchangeApi;
        this.binaryMessageCompression = new OkexBinaryMessageCompression();
    }

    @Override
    public DataSubscription subscribe(Market market, List<CandleInterval> intervals, boolean trades, boolean orderBook, boolean ticker, MarketDataReceiver dataReceiver) {

        List<CandleInterval> intervalList = intervals == null ? supportCandleIntervals() : intervals;
        List<String> channelList = new ArrayList<>(getKlineChannelList(market, intervalList));
        if (trades) {
            channelList.add(tradeChannelList(market));
        }
        if (orderBook) {
            channelList.addAll(orderBookChannelList(market));
        }
        if (ticker) {
            channelList.add(tickerChannelList(market));
        }

        try {
            exchangeApi.connectWebSocket(new OkexWebSocketHandler(binaryMessageCompression, channelList, dataReceiver, intervalList));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Okex websocket连接进行消息订阅出现异常,symbol:{},e:{}", market.getCoinPair().toSymbol(), e.toString());
        }

        return null;
    }

    @Override
    public List<CandleInterval> supportCandleIntervals() {
        /*return Arrays.asList(YEAR_1, MONTH_6, MONTH_3, MONTH_1, DAY_7, DAY_1, HOUR_12, HOUR_6,
                HOUR_4, HOUR_2, HOUR_1, MINUTES_30, MINUTES_15, MINUTES_5, MINUTES_3, MINUTES_1);*/
        return Arrays.asList(DAY_7, DAY_1, HOUR_12, HOUR_6,
                HOUR_4, HOUR_2, HOUR_1, MINUTES_30, MINUTES_15, MINUTES_5, MINUTES_3, MINUTES_1);
    }

    /**
     * K线
     *
     * @param market 市场
     * @return List<String>
     */
    private List<String> getKlineChannelList(Market market, List<CandleInterval> intervals) {
        List<String> channelList = new ArrayList<>();
        intervals.forEach(candleInterval -> {
            //K线
            switch (candleInterval) {
                case MONTH_1:
                    channelList.add("spot/candle" + TimeUnit.DAYS.toMillis(31) / 1000 + "s:" + coinPairSymbolConverter.coinPairToSymbol(market.getCoinPair()));
                    break;
                case MONTH_3:
                    channelList.add("spot/candle" + TimeUnit.DAYS.toMillis(93) / 1000 + "s:" + coinPairSymbolConverter.coinPairToSymbol(market.getCoinPair()));
                    break;
                case MONTH_6:
                    channelList.add("spot/candle" + TimeUnit.DAYS.toMillis(186) / 1000 + "s:" + coinPairSymbolConverter.coinPairToSymbol(market.getCoinPair()));
                    break;
                default:
                    channelList.add("spot/candle" + candleInterval.getMilliSeconds() / 1000 + "s:" + coinPairSymbolConverter.coinPairToSymbol(market.getCoinPair()));
                    break;
            }
        });
        return channelList;
    }

    /**
     * 24小时ticker
     *
     * @param market 市场币对信息
     * @return List<String>
     */
    private String tickerChannelList(Market market) {
        return "spot/ticker:" + coinPairSymbolConverter.coinPairToSymbol(market.getCoinPair());
    }

    /**
     * 最近成交
     *
     * @param market 市场币对信息
     * @return List<String>
     */
    private String tradeChannelList(Market market) {
        return "spot/trade:" + coinPairSymbolConverter.coinPairToSymbol(market.getCoinPair());
    }

    /**
     * 深度（5档，400档，400档增量）
     *
     * @param market 市场币对信息
     * @return List<String>
     */
    private List<String> orderBookChannelList(Market market) {
        return Arrays.asList(
                "spot/depth5:" + coinPairSymbolConverter.coinPairToSymbol(market.getCoinPair()),
                "spot/depth:" + coinPairSymbolConverter.coinPairToSymbol(market.getCoinPair()));

        //深度400档增量接口
        //"spot/depth_l2_tbt:" + coinPairSymbolConverter.coinPairToSymbol(market.getCoinPair());

    }

    @Override
    public long getMaxTickCountPerRequest() {
        return 300;
    }

    private final RateLimiter rateLimiter = RateLimiter.create(2.5);

    @Override
    public List<CandleTick> getHistory(Market market, CandleInterval interval, LocalDateTime start, LocalDateTime end) {

        rateLimiter.acquire();

        List<CandleTick> candleTickList = new ArrayList<>();
        RestTemplate restTemplate = exchangeApi.getRestTemplate();

        //周期做特殊处理
        String granularity = String.valueOf(interval.getMilliSeconds() / 1000);
        switch (interval) {
            case MONTH_1:
                granularity = String.valueOf(TimeUnit.DAYS.toMillis(31) / 1000);
                break;
            case MONTH_3:
                granularity = String.valueOf(TimeUnit.DAYS.toMillis(93) / 1000);
                break;
            case MONTH_6:
                granularity = String.valueOf(TimeUnit.DAYS.toMillis(186) / 1000);
                break;
        }

        String url = exchangeApi.getConnectionInfo().getRestfulApiEndpoint()
                + "/api/spot/v3/instruments/{instrument_id}/history/candles?granularity={granularity}&start={start}&end={end}";

        Map<String, String> param = new HashMap<>();
        param.put("instrument_id", coinPairSymbolConverter.coinPairToSymbol(market.getCoinPair()));
        param.put("granularity", granularity);
        param.put("start", DateUtils.getUtCTime(end));
        param.put("end", DateUtils.getUtCTime(start));

        HttpEntity<String> httpEntity = new HttpEntity<>(OkexFeatures.getHeaders());

        //log.info("请求头:{},参数:{}", url, param);
        ResponseEntity<JSONArray> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, JSONArray.class, param);
        //log.info("请求头:{},请求数据:{}", responseEntity.getHeaders(), responseEntity.getBody());

        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            Objects.requireNonNull(responseEntity.getBody());
            responseEntity.getBody().forEach(body -> {

                String[] kline = String.valueOf(body).trim().replace("[", "")
                        .replace("]", "").replace(" ", "").split(",");

                CandleTick candleTick = new CandleTick();
                candleTick.setTimeStamp(DateUtils.getTimeByUtc(kline[0]));
                candleTick.setOpen(new BigDecimal(String.valueOf(kline[1])));
                candleTick.setHigh(new BigDecimal(kline[2]));
                candleTick.setLow(new BigDecimal(kline[3]));
                candleTick.setClose(new BigDecimal(kline[4]));
                candleTick.setVolume(new BigDecimal(kline[5]));
                candleTickList.add(candleTick);
            });
        } else {
            throw new IllegalStateException(HttpMethod.GET + url + "请求失败,statusCode:" + responseEntity.getStatusCode());
        }
        return candleTickList;
    }

}
