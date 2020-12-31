package com.kevin.gateway.core;

/**
 * 交易工具标识符
 */
public interface InstrumentId extends Symbolic {
    InstrumentType getType();
}
