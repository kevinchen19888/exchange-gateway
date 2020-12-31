package com.kevin.gateway.core;

import lombok.Data;
import lombok.ToString;

import java.util.Objects;
import java.util.function.Function;

/**
 * 用户凭证对象
 */
@Data(staticConstructor = "of")
public final class Credentials {

    private final String apiKey;
    @ToString.Exclude
    private final String secretKey;
    @ToString.Exclude
    private final String passphrase;

    /**
     * 从环境变量中创建用户凭证
     * <p>
     * 如果环境中没有设置对应的键值，则对应字段为 null
     *
     * <pre>
     *     Credentials c = Credentials.fromEnv("OKEX_");
     * </pre>
     *
     * @param prefix 环境变量的前缀，比如：OKEX_
     * @return 用户凭证
     */
    public static Credentials fromEnv(String prefix) {
        Function<String,String> func = !Objects.isNull(System.getenv(prefix + "APIKEY")) ? System::getenv : System::getProperty;
        String apiKey = func.apply(prefix + "APIKEY");
        String secretKey = func.apply(prefix + "SECRET_KEY");
        String passphrase = func.apply(prefix + "PASSPHRASE");
        return of(apiKey, secretKey, passphrase);
    }
}
