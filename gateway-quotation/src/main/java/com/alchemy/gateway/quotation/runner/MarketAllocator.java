package com.alchemy.gateway.quotation.runner;

import com.alchemy.gateway.market.ExchangeMarket;
import com.alchemy.gateway.market.MarketLoader;
import com.alchemy.gateway.quotation.service.QuotationHistoryFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MarketAllocator implements CommandLineRunner {

    private final QuotationHistoryFetcher quotationHistoryFetcher;
    private final MarketLoader marketLoader;

    @Autowired
    public MarketAllocator(QuotationHistoryFetcher quotationHistoryFetcher, MarketLoader marketLoader) {
        this.quotationHistoryFetcher = quotationHistoryFetcher;
        this.marketLoader = marketLoader;
    }

    @Override
    public void run(String... args) {
        List<ExchangeMarket> exchangeMarketList = marketLoader.loadExchangeMarkets();

        //现货
        quotationHistoryFetcher.klineHistory(exchangeMarketList);
    }
}
