package com.alchemy.gateway.broker.exception;

import com.alchemy.gateway.core.GatewayException;

/**
 * describe:
 *
 * @author zoulingwei
 */
public class OrderNotExistException extends GatewayException {
    public OrderNotExistException(String msg) {
        super(msg);
    }
}
