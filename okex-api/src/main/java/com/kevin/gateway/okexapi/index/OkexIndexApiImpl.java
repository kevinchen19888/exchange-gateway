package com.kevin.gateway.okexapi.index;

import com.kevin.gateway.core.CoinPair;
import com.kevin.gateway.okexapi.base.util.OkexEnvironment;
import com.kevin.gateway.okexapi.base.rest.PublicGetTemplate;
import com.kevin.gateway.okexapi.base.rest.impl.OkexAbstractImpl;
import com.kevin.gateway.okexapi.index.response.OkexSearchIndexConstituentsResponse;

import java.time.Duration;

public class OkexIndexApiImpl extends OkexAbstractImpl implements OkexIndexApi {
    private static PublicGetTemplate SEARCH_INDEX_CONSTITUENTS = PublicGetTemplate
            .of("/api/index/v3/{instrument_id}/constituents", 20, Duration.ofSeconds(2));


    public OkexIndexApiImpl(OkexEnvironment environment) {
        super(environment);
    }

    @Override
    public OkexSearchIndexConstituentsResponse searchIndexConstituents(CoinPair coinPair) {

        return SEARCH_INDEX_CONSTITUENTS.bind(environment)
                .getForObject(OkexSearchIndexConstituentsResponse.class, coinPair.getSymbol());
    }
}
