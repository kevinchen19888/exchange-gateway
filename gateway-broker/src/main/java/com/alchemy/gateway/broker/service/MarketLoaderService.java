package com.alchemy.gateway.broker.service;

import com.alchemy.gateway.core.common.CoinPair;
import com.alchemy.gateway.market.ExchangeMarket;
import com.alchemy.gateway.market.MarketLoader;
import com.alchemy.gateway.market.entity.Market;
import com.alchemy.gateway.market.repository.MarketRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * describe:
 *
 * @author zoulingwei
 */
@Service
public class MarketLoaderService implements MarketLoader {

    private final MarketRepository marketRepository;

    public MarketLoaderService(MarketRepository marketRepository) {
        this.marketRepository = marketRepository;
    }

    @Override
    public List<ExchangeMarket> loadExchangeMarkets() {
        List<ExchangeMarket> result = new ArrayList<>();
        List<Market> spot = marketRepository.findAllByMarketType("spot");
        spot.forEach(market -> {
            ExchangeMarket exchangeMarket = new ExchangeMarket(market.getExchangeName(), com.alchemy.gateway.core.common.Market.spotMarket(CoinPair.fromSymbol(market.getCoinPair()), market.getPricePrecision()));
            result.add(exchangeMarket);
        });
        return result;
    }
}
