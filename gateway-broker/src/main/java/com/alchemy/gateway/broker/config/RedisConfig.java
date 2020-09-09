package com.alchemy.gateway.broker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class RedisConfig {

    @Bean
    HashOperations<String, String, String> hashOperations(StringRedisTemplate redisTemplate) {
        return redisTemplate.opsForHash();
    }
}
