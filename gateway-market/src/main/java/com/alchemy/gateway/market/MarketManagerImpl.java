package com.alchemy.gateway.market;

import com.alchemy.gateway.core.common.CoinPair;
import com.alchemy.gateway.core.common.Market;

import java.util.*;


public class MarketManagerImpl implements MarketManager {
    private final Map<String, List<Market>> exchangeMarketMap = new TreeMap<>();
    private final List<ExchangeMarket> exchangeMarkets = new ArrayList<>();

    @Override
    public void load(MarketLoader marketLoader) {
        List<ExchangeMarket> loadedExchangeMarkets = marketLoader.loadExchangeMarkets();
        exchangeMarketMap.clear();
        exchangeMarkets.clear();

        // 根据装载的交易所市场，更新
        //      "交易所 -> 市场列表"的映射，和
        //      全部交易所市场的列表
        for (ExchangeMarket exchangeMarket : loadedExchangeMarkets) {
            if (!exchangeMarketMap.containsKey(exchangeMarket.getExchangeName())) {
                List<Market> markets = new ArrayList<>();
                markets.add(exchangeMarket.getMarket());
                exchangeMarketMap.put(exchangeMarket.getExchangeName(), markets);
            } else {
                List<Market> markets = exchangeMarketMap.get(exchangeMarket.getExchangeName());
                markets.add(exchangeMarket.getMarket());
            }
        }
        this.exchangeMarkets.addAll(loadedExchangeMarkets);
    }

    @Override
    public List<ExchangeMarket> getExchangeMarkets() {
        return this.exchangeMarkets;
    }

    @Override
    public Set<String> getExchangeNames() {
        return exchangeMarketMap.keySet();
    }

    @Override
    public List<Market> getMarkets(String exchangeName) {
        return exchangeMarketMap.get(exchangeName);
    }

    @Override
    public boolean exists(String exchangeName, Market market) {
        for (ExchangeMarket exchangeMarket : this.exchangeMarkets) {
            if (exchangeMarket.getExchangeName().equals(exchangeName)
                    && exchangeMarket.getMarket().equals(market)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Market getMarket(String exchangeName, CoinPair coinPair) {
        for (Market market : exchangeMarketMap.get(exchangeName)) {
            if (market.getCoinPair().toSymbol().equals(coinPair.toSymbol())){
                return market;
            }
        }
        throw new RuntimeException("市场不存在:"+exchangeName+":"+coinPair.toSymbol());
    }
}
