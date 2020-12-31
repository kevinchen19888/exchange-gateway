package com.kevin.gateway.okexapi.future.service.impl;

import com.kevin.gateway.okexapi.base.util.OkexEnvironment;
import com.kevin.gateway.okexapi.base.rest.PublicGetTemplate;
import com.kevin.gateway.okexapi.base.rest.PublicGetTemplateClient;
import com.kevin.gateway.okexapi.base.rest.impl.OkexAbstractImpl;
import com.kevin.gateway.okexapi.future.data.model.*;
import com.kevin.gateway.okexapi.future.service.FutureDataApi;
import com.kevin.gateway.okexapi.option.util.TimeGranularity;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class FutureDataApiImpl extends OkexAbstractImpl implements FutureDataApi {

    private static final PublicGetTemplate GET_LONG_SHORT_RATIO = PublicGetTemplate
            .of("/api/information/v3/{currency}/long_short_ratio", 20, Duration.ofSeconds(2L));

    private static final PublicGetTemplate GET_VOLUME = PublicGetTemplate
            .of("/api/information/v3/{currency}/volume", 20, Duration.ofSeconds(2L));

    private static final PublicGetTemplate GET_SENTIMENT = PublicGetTemplate
            .of("/api/information/v3/{currency}/sentiment", 20, Duration.ofSeconds(2L));

    private static final PublicGetTemplate GET_TAKER = PublicGetTemplate
            .of("/api/information/v3/{currency}/taker", 20, Duration.ofSeconds(2L));

    private static final PublicGetTemplate GET_MARGIN = PublicGetTemplate
            .of("/api/information/v3/{currency}/margin", 20, Duration.ofSeconds(2L));


    public FutureDataApiImpl(OkexEnvironment environment) {
        super(environment);
    }


    @Override
    public FutureLongShortRatioResponse[] getLongShortRatio(String currency, LocalDateTime start, LocalDateTime end, TimeGranularity granularity) {
        PublicGetTemplateClient client = GET_LONG_SHORT_RATIO.bind(environment);

        if (currency != null) {

            Map<String, Object> uriVariables = new HashMap<>();
            uriVariables.put("currency", currency);
            client.getUriComponentsBuilder().uriVariables(uriVariables);
        }

        if (start != null) {
            client.getUriComponentsBuilder().queryParam("start", start.toString());
        }
        if (end != null) {
            client.getUriComponentsBuilder().queryParam("end", end.toString());
        }
        if (granularity != null) {
            client.getUriComponentsBuilder().queryParam("granularity", granularity.getVal());
        }

        return client.getForObject(FutureLongShortRatioResponse[].class);


    }

    @Override
    public FutureVolumeResponse[] getVolume(String currency, LocalDateTime start, LocalDateTime end, TimeGranularity granularity) {

        PublicGetTemplateClient client = GET_VOLUME.bind(environment);

        if (currency != null) {

            Map<String, Object> uriVariables = new HashMap<>();
            uriVariables.put("currency", currency);
            client.getUriComponentsBuilder().uriVariables(uriVariables);
        }

        if (start != null) {
            client.getUriComponentsBuilder().queryParam("start", start.toString());
        }
        if (end != null) {
            client.getUriComponentsBuilder().queryParam("end", end.toString());
        }
        if (granularity != null) {
            client.getUriComponentsBuilder().queryParam("granularity", granularity.getVal());
        }

        return client.getForObject(FutureVolumeResponse[].class);

    }

    @Override
    public FutureTakerResponse[] getTaker(String currency, LocalDateTime start, LocalDateTime end, TimeGranularity granularity) {
        PublicGetTemplateClient client = GET_TAKER.bind(environment);

        if (currency != null) {

            Map<String, Object> uriVariables = new HashMap<>();
            uriVariables.put("currency", currency);
            client.getUriComponentsBuilder().uriVariables(uriVariables);
        }

        if (start != null) {
            client.getUriComponentsBuilder().queryParam("start", start.toString());
        }
        if (end != null) {
            client.getUriComponentsBuilder().queryParam("end", end.toString());
        }
        if (granularity != null) {
            client.getUriComponentsBuilder().queryParam("granularity", granularity.getVal());
        }

        return client.getForObject(FutureTakerResponse[].class);

    }

    @Override
    public FutureSentimentResponse[] getSentiment(String currency, LocalDateTime start, LocalDateTime end, TimeGranularity granularity) {
        PublicGetTemplateClient client = GET_SENTIMENT.bind(environment);

        if (currency != null) {

            Map<String, Object> uriVariables = new HashMap<>();
            uriVariables.put("currency", currency);
            client.getUriComponentsBuilder().uriVariables(uriVariables);
        }

        if (start != null) {
            client.getUriComponentsBuilder().queryParam("start", start.toString());
        }
        if (end != null) {
            client.getUriComponentsBuilder().queryParam("end", end.toString());
        }
        if (granularity != null) {
            client.getUriComponentsBuilder().queryParam("granularity", granularity.getVal());
        }

        return client.getForObject(FutureSentimentResponse[].class);

    }

    @Override
    public FutureMarginResponse[] getMargin(String currency, LocalDateTime start, LocalDateTime end, TimeGranularity granularity) {
        PublicGetTemplateClient client = GET_MARGIN.bind(environment);

        if (currency != null) {

            Map<String, Object> uriVariables = new HashMap<>();
            uriVariables.put("currency", currency);
            client.getUriComponentsBuilder().uriVariables(uriVariables);
        }

        if (start != null) {
            client.getUriComponentsBuilder().queryParam("start", start.toString());
        }
        if (end != null) {
            client.getUriComponentsBuilder().queryParam("end", end.toString());
        }
        if (granularity != null) {
            client.getUriComponentsBuilder().queryParam("granularity", granularity.getVal());
        }

        return client.getForObject(FutureMarginResponse[].class);
    }
}
