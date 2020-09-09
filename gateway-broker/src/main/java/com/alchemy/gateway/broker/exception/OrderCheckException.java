package com.alchemy.gateway.broker.exception;

import com.alchemy.gateway.core.GatewayException;

/**
 * describe:
 *
 * @author zoulingwei
 */
public class OrderCheckException extends GatewayException {
    public OrderCheckException(String msg) {
        super(msg);
    }
}
