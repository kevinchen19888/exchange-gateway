package com.alchemy.gateway.broker.exception;

import com.alchemy.gateway.core.GatewayException;

/**
 * describe:
 *
 * @author zoulingwei
 */
public class CredentialsNotExistException extends GatewayException {
    public CredentialsNotExistException(String msg) {
        super(msg);
    }
}
