package com.alchemy.gateway.core.common;

import lombok.Data;

@Data(staticConstructor = "of")
public class AccountInfo {
    private final String exchangeName; // 交易所名称，比如：huobi, okex, ...

    private final Credentials credentials;    // 凭据
}
