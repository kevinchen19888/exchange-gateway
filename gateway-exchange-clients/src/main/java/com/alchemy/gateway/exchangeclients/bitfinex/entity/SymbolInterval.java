package com.alchemy.gateway.exchangeclients.bitfinex.entity;

import com.alchemy.gateway.core.common.CandleInterval;
import lombok.Data;


@Data
public class SymbolInterval {

    private String symbol;

    private CandleInterval interval;
}
