package com.kevin.gateway.core;

import lombok.Data;

@Data(staticConstructor = "of")
public final class FiatCoin {
    private final String symbol;
}
