package com.alchemy.gateway.core;

import org.springframework.web.client.RestTemplate;

/**
 * 可注入 RestTemplate 接口
 */
public interface RestTemplateAware {
    /**
     * 设置 RestTemplate
     *
     * @param restTemplate RestTemplate 接口
     */
    void setRestTemplate(RestTemplate restTemplate);
}
