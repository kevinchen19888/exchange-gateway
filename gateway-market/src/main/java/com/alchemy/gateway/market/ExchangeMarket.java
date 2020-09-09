package com.alchemy.gateway.market;

import com.alchemy.gateway.core.common.Market;
import lombok.Data;

@Data
public final class ExchangeMarket {
    private final String exchangeName;
    private final Market market;
}
