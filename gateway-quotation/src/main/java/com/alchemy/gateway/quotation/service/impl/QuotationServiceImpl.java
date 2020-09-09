package com.alchemy.gateway.quotation.service.impl;

import com.alchemy.gateway.core.ExchangeApi;
import com.alchemy.gateway.core.ExchangeManager;
import com.alchemy.gateway.core.common.CandleInterval;
import com.alchemy.gateway.core.common.Market;
import com.alchemy.gateway.core.marketdata.*;
import com.alchemy.gateway.core.utils.DateUtils;
import com.alchemy.gateway.market.ExchangeMarket;
import com.alchemy.gateway.quotation.entity.Kline;
import com.alchemy.gateway.quotation.service.HistoryWritor;
import com.alchemy.gateway.quotation.service.QuotationHistoryFetcher;
import com.alchemy.gateway.quotation.service.QuotationReceiver;
import com.alchemy.gateway.quotation.service.QuotationSender;
import com.alchemy.gateway.quotation.util.RabbitConfigUtils;
import com.alchemy.gateway.quotation.util.RoutingKey;
import com.alchemy.gateway.quotation.vo.KlineVo;
import com.alchemy.gateway.quotation.vo.OrderBookVo;
import com.alchemy.gateway.quotation.vo.TickerVo;
import com.alchemy.gateway.quotation.vo.TradeTickVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class QuotationServiceImpl implements QuotationSender, QuotationHistoryFetcher, QuotationReceiver {

    private final RabbitTemplate template;
    private final HistoryWritor historyWritor;
    private final ExchangeManager exchangeManager;

    @Value(value = "${gateway.instance.klineHistoryTime}")
    private String klineHistoryTime;


    @Autowired
    public QuotationServiceImpl(RabbitTemplate template, HistoryWritor historyWritor, ExchangeManager exchangeManager) {
        this.template = template;
        this.historyWritor = historyWritor;
        this.exchangeManager = exchangeManager;
    }

    /*@Override
    public void subscribe(List<ExchangeMarket> exchangeMarketList) {
        //处理好订阅频道数据
        exchangeMarketList.forEach(coinPair -> exchangeManager.getAPI(coinPair.getExchangeName()).getMarketDataApi()
                .subscribe(Market.spotMarket(coinPair.getMarket().getCoinPair()), new MarketDataReceiver() {
                    @Override
                    public void receiveCandles(CandleInterval candleInterval, CandleTick candleTick) {
                        //historyWritor.saveCandleTick(candleTick, candleInterval, coinPair.getExchangeName(), coinPair.getMarket());
                        sendKlines(candleTick, coinPair, candleInterval);
                    }

                    @Override
                    public void receiveOrderBook(OrderBook orderBook) {
                        sendOrderBooks(orderBook, coinPair);
                    }

                    @Override
                    public void receiveTrades(List<TradeTick> tradeTick) {
                        sendTrades(tradeTick, coinPair);
                    }

                    @Override

                    public void receive24Tickers(Ticker ticker) {
                        sendTickers(ticker, coinPair);
                    }
                }));
    }*/

    @Override
    public void subscribe(ExchangeMarket coinPair) {
        //处理好订阅频道数据
        exchangeManager.getAPI(coinPair.getExchangeName()).getMarketDataApi()
                .subscribe(Market.spotMarket(coinPair.getMarket().getCoinPair()), new MarketDataReceiver() {
                    @Override
                    public void receiveCandles(CandleInterval candleInterval, CandleTick candleTick) {
                        sendKlines(candleTick, coinPair, candleInterval);
                    }

                    @Override
                    public void receiveOrderBook(OrderBook orderBook) {
                        sendOrderBooks(orderBook, coinPair);
                    }

                    @Override
                    public void receiveTrades(List<TradeTick> tradeTick) {
                        sendTrades(tradeTick, coinPair);
                    }

                    @Override

                    public void receive24Tickers(Ticker ticker) {
                        sendTickers(ticker, coinPair);
                    }
                });
    }

    @Override
    public void sendKlines(CandleTick candleTick, ExchangeMarket coinPair, CandleInterval candleInterval) {
        try {
            KlineVo.DataDetail dataDetail = KlineVo.DataDetail.builder()
                    .close(candleTick.getClose())
                    .high(candleTick.getHigh())
                    .low(candleTick.getLow())
                    .open(candleTick.getOpen())
                    .timestamp(DateUtils.getEpochMilliByTime(candleTick.getTimeStamp()))
                    .reserveMap(candleTick.getReserveMap())
                    .volume(candleTick.getVolume())
                    .build();

            List<KlineVo.DataDetail> dataDetails = new ArrayList<>();
            dataDetails.add(dataDetail);

            KlineVo klineVo = KlineVo.builder()
                    .exchange(coinPair.getExchangeName())
                    .klineType(candleInterval.getDescribe())
                    .marketType(coinPair.getMarket().getType().name())
                    .symbol(coinPair.getMarket().getCoinPair().toSymbol())
                    .dataDetail(dataDetails)
                    .build();

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(klineVo);
            String routingKey = new RoutingKey(".")
                    .addPart(RabbitConfigUtils.ALCHEMY)
                    .addPart(RabbitConfigUtils.GATEWAY)
                    .addPart(RabbitConfigUtils.ROUTING_KEY_KLINE)
                    .addPart(coinPair.getExchangeName())
                    .addPart(coinPair.getMarket().getType().name())
                    .addPart(coinPair.getMarket().getCoinPair().toSymbol())
                    .addPart(candleInterval.getDescribe())
                    .toString();
            template.convertAndSend(RabbitConfigUtils.TOPIC_NAME_DEFAULT, routingKey, json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendOrderBooks(OrderBook orderBook, ExchangeMarket coinPair) {
        try {
            List<OrderBookVo.OrderBookDetail.OrderDetail> asks = new ArrayList<>();
            orderBook.getAsks().forEach(ask -> {
                OrderBookVo.OrderBookDetail.OrderDetail orderDetail = new OrderBookVo.OrderBookDetail.OrderDetail();
                orderDetail.setPrice(ask.getPrice());
                orderDetail.setVolume(ask.getSize());
                orderDetail.setReserveMap(ask.getReserveMap());
                asks.add(orderDetail);
            });
            List<OrderBookVo.OrderBookDetail.OrderDetail> bids = new ArrayList<>();
            orderBook.getBids().forEach(bid -> {
                OrderBookVo.OrderBookDetail.OrderDetail orderDetail = new OrderBookVo.OrderBookDetail.OrderDetail();
                orderDetail.setPrice(bid.getPrice());
                orderDetail.setVolume(bid.getSize());
                orderDetail.setReserveMap(bid.getReserveMap());
                bids.add(orderDetail);
            });
            OrderBookVo.OrderBookDetail orderBookDetail = new OrderBookVo.OrderBookDetail();
            orderBookDetail.setAsks(asks);
            orderBookDetail.setBids(bids);
            orderBookDetail.setTimestamp(DateUtils.getEpochMilliByTime(orderBook.getTimeStamp()));
            orderBookDetail.setLevel(orderBook.getLevel());

            List<OrderBookVo.OrderBookDetail> orderBookDetails = new ArrayList<>();
            orderBookDetails.add(orderBookDetail);

            OrderBookVo orderBookVo = new OrderBookVo();
            orderBookVo.setExchange(coinPair.getExchangeName());
            orderBookVo.setSymbol(coinPair.getMarket().getCoinPair().toSymbol());
            orderBookVo.setMarketType(coinPair.getMarket().getType().name());
            orderBookVo.setDataDetail(orderBookDetails);

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(orderBookVo);

            String routingKey = new RoutingKey(".")
                    .addPart(RabbitConfigUtils.ALCHEMY)
                    .addPart(RabbitConfigUtils.GATEWAY)
                    .addPart(RabbitConfigUtils.ROUTING_KEY_DEPTH)
                    .addPart(coinPair.getExchangeName())
                    .addPart(coinPair.getMarket().getType().name())
                    .addPart(coinPair.getMarket().getCoinPair().toSymbol())
                    .addPart(String.valueOf(orderBook.getLevel()))
                    .toString();

            template.convertAndSend(RabbitConfigUtils.TOPIC_NAME_DEFAULT, routingKey, json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendTrades(List<TradeTick> items, ExchangeMarket coinPair) {
        try {

            List<TradeTickVo.DataDetail> dataDetails = new ArrayList<>();
            items.forEach(tradeTick -> {
                TradeTickVo.DataDetail dataDetail = new TradeTickVo.DataDetail();
                dataDetail.setPrice(tradeTick.getPrice());
                dataDetail.setTimestamp(DateUtils.getEpochMilliByTime(tradeTick.getTimeStamp()));
                dataDetail.setTradeId(tradeTick.getTradeId());
                dataDetail.setVolume(tradeTick.getVolume());
                dataDetail.setSide(tradeTick.getSide());
                dataDetail.setReserveMap(tradeTick.getReserveMap());
                dataDetails.add(dataDetail);
            });

            TradeTickVo tradeTickVo = new TradeTickVo();
            tradeTickVo.setExchange(coinPair.getExchangeName());
            tradeTickVo.setMarketType(coinPair.getMarket().getType().name());
            tradeTickVo.setSymbol(coinPair.getMarket().getCoinPair().toSymbol());
            tradeTickVo.setDataDetail(dataDetails);

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(tradeTickVo);

            String routingKey = new RoutingKey(".")
                    .addPart(RabbitConfigUtils.ALCHEMY)
                    .addPart(RabbitConfigUtils.GATEWAY)
                    .addPart(RabbitConfigUtils.ROUTING_KEY_TRADE)
                    .addPart(coinPair.getExchangeName())
                    .addPart(coinPair.getMarket().getType().name())
                    .addPart(coinPair.getMarket().getCoinPair().toSymbol())
                    .toString();

            template.convertAndSend(RabbitConfigUtils.TOPIC_NAME_DEFAULT, routingKey, json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendTickers(Ticker ticker, ExchangeMarket coinPair) {
        try {
            List<TickerVo.DataDetail> items = new ArrayList<>();
            TickerVo.DataDetail item = new TickerVo.DataDetail();
            item.setAmount(ticker.getAmount());
            item.setAsk1Price(ticker.getAsk1Price());
            item.setAsk1Volume(ticker.getAsk1Volume());
            item.setBid1Price(ticker.getBid1Price());
            item.setBid1Volume(ticker.getBid1Volume());
            item.setClose(ticker.getClose());
            item.setHigh(ticker.getHigh());
            item.setLow(ticker.getLow());
            item.setOpen(ticker.getOpen());
            item.setTimestamp(DateUtils.getEpochMilliByTime(ticker.getTimeStamp()));
            item.setReserveMap(ticker.getReserveMap());
            item.setVolume(ticker.getVolume());
            item.setVolumeClose(ticker.getVolumeClose());
            items.add(item);

            TickerVo tickerVo = new TickerVo();
            tickerVo.setExchange(coinPair.getExchangeName());
            tickerVo.setMarketType(coinPair.getMarket().getType().name());
            tickerVo.setSymbol(coinPair.getMarket().getCoinPair().toSymbol());
            tickerVo.setDataDetail(items);

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(tickerVo);

            String routingKey = new RoutingKey(".")
                    .addPart(RabbitConfigUtils.ALCHEMY)
                    .addPart(RabbitConfigUtils.GATEWAY)
                    .addPart(RabbitConfigUtils.ROUTING_KEY_TICKER)
                    .addPart(coinPair.getExchangeName())
                    .addPart(coinPair.getMarket().getType().name())
                    .addPart(coinPair.getMarket().getCoinPair().toSymbol())
                    .toString();

            template.convertAndSend(RabbitConfigUtils.TOPIC_NAME_DEFAULT, routingKey, json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void klineHistory(List<ExchangeMarket> exchangeMarketList) {
        exchangeMarketList.forEach(coinPairInfo -> {
            ExchangeApi exchangeAPI = exchangeManager.getAPI(coinPairInfo.getExchangeName());
            //拿到各交易所的周期
            List<CandleInterval> candleIntervalList = exchangeAPI.getMarketDataApi().supportCandleIntervals();
            candleIntervalList.forEach(candleInterval -> {
                log.info("{}开始拉取周期为{}的数据", coinPairInfo.getExchangeName(), candleInterval.getDescribe());
                HistoryCrawler historyCrawler = new HistoryCrawler(exchangeAPI, coinPairInfo.getMarket());

                //往前爬数据
                Kline kline = historyWritor.findKlineOldest(coinPairInfo.getExchangeName(), coinPairInfo.getMarket().getCoinPair().toSymbol(), candleInterval.getDescribe());
                getHistoryCrawler(coinPairInfo, candleInterval, historyCrawler, kline, true);

                //往后爬数据
                kline = historyWritor.findKlineLatest(coinPairInfo.getExchangeName(), coinPairInfo.getMarket().getCoinPair().toSymbol(), candleInterval.getDescribe());
                getHistoryCrawler(coinPairInfo, candleInterval, historyCrawler, kline, false);
            });
            //webSocket订阅
            subscribe(coinPairInfo);
        });
    }

    private void getHistoryCrawler(ExchangeMarket coinPairInfo, CandleInterval candleInterval, HistoryCrawler historyCrawler, Kline kline, boolean flag) {
        LocalDateTime startTime = LocalDateTime.parse(klineHistoryTime);
        if (kline != null) {
            startTime = kline.getTimeStamp();
        }
        historyCrawler.start(candleInterval, startTime, flag);
        while (historyCrawler.hasNext()) {
            List<CandleTick> candleTickList = historyCrawler.next();
            historyWritor.saveKlineHistory(candleTickList, candleInterval, coinPairInfo.getExchangeName(), coinPairInfo.getMarket());
        }
    }
}
