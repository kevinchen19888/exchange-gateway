package com.alchemy.gateway.core.common;

/**
 * 市场类型
 */
public enum MarketType {
    SPOT(false),    // 现货
    FUTURE(true),   // 期货
    OPTION(true);   // 期权

    private final boolean isDerivative; // 是否衍生品市场

    MarketType(boolean isDerivative) {
        this.isDerivative = isDerivative;
    }

    public boolean isDerivative() {
        return isDerivative;
    }
}
