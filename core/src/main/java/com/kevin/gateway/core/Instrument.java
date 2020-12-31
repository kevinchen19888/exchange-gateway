package com.kevin.gateway.core;

/**
 * 交易工具
 *
 * @param <ID> 交易工具ID类型
 */
public interface Instrument<ID extends InstrumentId> {
    ID getId();
}
