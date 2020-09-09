package com.alchemy.gateway.core.common;

import com.google.common.util.concurrent.RateLimiter;

/**
 * @author kevin chen
 */
public interface RateLimiterManager {

    /**
     * 获取基于apiKey的RateLimiter
     *
     * @param key       示例:exchangeName+methodName+apiKey
     * @param frequency permitsPerSecond(每秒钟允许请求次数)
     * @return RateLimiter
     */
    RateLimiter getRateLimiter(String key, double frequency);

}
