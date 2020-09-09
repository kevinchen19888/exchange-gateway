package com.alchemy.gateway.exchangeclients.okex.resultModel;

import lombok.Data;

import java.util.List;

@Data
public class SpotKline {
    private String instrument_id;
    private List<String> candle;
}
