package com.alchemy.gateway.core.common;

import com.google.common.util.concurrent.RateLimiter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author kevin chen
 */
public class RateLimiterManagerImpl implements RateLimiterManager {
    private final Map<String, RateLimiter> rateLimiterMap = new ConcurrentHashMap<>(1024);

    @Override
    public RateLimiter getRateLimiter(String key, double frequency) {
        if (!this.rateLimiterMap.containsKey(key)) {
            this.rateLimiterMap.putIfAbsent(key, RateLimiter.create(frequency));
        }
        return this.rateLimiterMap.get(key);
    }

}
