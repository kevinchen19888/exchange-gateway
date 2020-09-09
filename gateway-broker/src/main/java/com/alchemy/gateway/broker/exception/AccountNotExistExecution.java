package com.alchemy.gateway.broker.exception;

import com.alchemy.gateway.core.GatewayException;

/**
 * describe:
 *
 * @author zoulingwei
 */
public class AccountNotExistExecution extends GatewayException {
    public AccountNotExistExecution(String msg) {
        super(msg);
    }
}
