package com.alchemy.gateway.core.common;

import lombok.Data;
import lombok.ToString;

/**
 * 凭证，代表交易所账户
 */
@Data(staticConstructor = "of")
public class Credentials {

    private final String apiKey;
    @ToString.Exclude
    private final String secretKey;
    @ToString.Exclude
    private final String passphrase;
}
