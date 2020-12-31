package com.kevin.gateway.okexapi.option.domain;

import com.kevin.gateway.core.CoinPair;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OptionBatchAmendOrderRequest {
    private CoinPair underlying;
    private List<OptionAmendOrderRequest> amendData;
}
