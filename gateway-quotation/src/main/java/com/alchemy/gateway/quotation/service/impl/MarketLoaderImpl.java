package com.alchemy.gateway.quotation.service.impl;

import com.alchemy.gateway.core.common.CoinPair;
import com.alchemy.gateway.market.ExchangeMarket;
import com.alchemy.gateway.market.MarketLoader;
import com.alchemy.gateway.market.entity.Market;
import com.alchemy.gateway.market.repository.MarketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class MarketLoaderImpl implements MarketLoader {
    private final MarketRepository marketRepository;

    @Value(value = "${gateway.instance.nodeId}")
    private String nodeId;  // 节点 id

    @Value(value = "${gateway.instance.limit}")
    private int maxCount;   // 最多处理市场数

    @Autowired
    public MarketLoaderImpl(MarketRepository marketRepository) {
        this.marketRepository = marketRepository;
    }

    protected void markNode(String nodeId, int maxCount) {
        // 标记市场为指定 nodeId
        List<Market> marketList = marketRepository.findAllByNodeId(nodeId);
        if (marketList.size() < maxCount) {
            marketList = marketRepository.findAllByNodeIdIsNull(0, maxCount - marketList.size());
            marketList.forEach(item -> item.setNodeId(nodeId));
        }
        if (marketList.size() > maxCount) {
            marketList = marketRepository.findAllByNodeId(nodeId, maxCount, marketList.size());
            marketList.forEach(item -> item.setNodeId(null));
        }
        marketRepository.saveAll(marketList);
    }


    protected List<ExchangeMarket> loadMarked(String nodeId) {
        // 装载标记后的市场
        List<ExchangeMarket> exchangeMarketList = new ArrayList<>();

        List<Market> marketList = marketRepository.findAllByNodeId(nodeId);
        marketList.forEach(market -> {
            ExchangeMarket exchangeMarket = new ExchangeMarket(market.getExchangeName(), com.alchemy.gateway.core.common.Market.spotMarket(CoinPair.fromSymbol(market.getCoinPair()),market.getPricePrecision()));
            exchangeMarketList.add(exchangeMarket);
        });
        return exchangeMarketList;
    }

    @Override
    @Transactional
    public List<ExchangeMarket> loadExchangeMarkets() {
        markNode(this.nodeId, this.maxCount);
        return loadMarked(this.nodeId);
    }
}
