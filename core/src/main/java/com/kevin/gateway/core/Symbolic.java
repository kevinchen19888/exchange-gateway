package com.kevin.gateway.core;

/**
 * 此接口代表具有标识符的特征
 */
public interface Symbolic {
    /**
     * 返回标识符
     * <p>
     * 比如：
     * 对于币种，返回 BTC、ETH、……等；
     * 对于币对，返回 BTC-USDT、ETH-BTC……等
     *
     * @return 标识符
     */
    String getSymbol();
}
