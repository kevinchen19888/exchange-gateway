package com.kevin.gateway.okexapi.base.websocket.request;

import com.kevin.gateway.core.Credentials;

import java.util.ArrayList;
import java.util.List;

/**
 * 登录指令构建器
 * <p>
 * 格式：
 * <pre>
 *     {"op":"login","args":["api_key","passphrase","timestamp","sign"]
 * </pre>
 * <p>
 * 例如：
 * <pre>
 *     {"op":"login","args":["985d5b66-57ce-40fb-b714-afc0b9787083","123456","1538054050.975",
 * "7L+zFQ+CEgGu5rzCj4+BdV2/uUHGqddA9pI6ztsRRPs="]}
 * </pre>
 * <p>
 * 响应：
 * <pre>
 *     {"event":"login","success":true}
 * </pre>
 */
public class LoginOpBuilder implements OperationBuilder {
    private final Credentials credentials;

    public LoginOpBuilder(Credentials credentials) {
        this.credentials = credentials;
    }

    private long timestamp() {
        return System.currentTimeMillis() / 1000;
    }

    private String sign(long timestamp) {
        return SignTools.sign(this.credentials, timestamp);
    }


    @Override
    public Operation build() {
        return new Operation() {
            @Override
            public String getOp() {
                return Op.LOGIN.getValue();
            }

            @Override
            public List<String> getArgs() {
                List<String> args = new ArrayList<>();
                args.add(credentials.getApiKey());
                args.add(credentials.getPassphrase());
                long timestamp = timestamp();
                args.add(Long.toString(timestamp));
                args.add(sign(timestamp));
                return args;
            }
        };
    }
}
