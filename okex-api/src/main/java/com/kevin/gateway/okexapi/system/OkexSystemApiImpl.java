package com.kevin.gateway.okexapi.system;

import com.kevin.gateway.okexapi.base.util.OkexEnvironment;
import com.kevin.gateway.okexapi.base.rest.PublicGetTemplate;
import com.kevin.gateway.okexapi.base.rest.PublicGetTemplateClient;
import com.kevin.gateway.okexapi.base.rest.impl.OkexAbstractImpl;
import com.kevin.gateway.okexapi.system.response.SystemStatus;
import com.kevin.gateway.okexapi.system.response.SystemStatusResponse;
import org.springframework.lang.Nullable;

import java.time.Duration;

public class OkexSystemApiImpl extends OkexAbstractImpl implements OkexSystemApi {
    private static final PublicGetTemplate GET_STATUSES = PublicGetTemplate
            .of("/api/system/v3/status", 1, Duration.ofSeconds(5L));

    // 实现
    public OkexSystemApiImpl(OkexEnvironment environment) {
        super(environment);
    }

    @Override
    public SystemStatusResponse[] getStatuses(@Nullable SystemStatus systemStatus) {
        PublicGetTemplateClient client = GET_STATUSES.bind(environment);
        if (systemStatus != null) {
            client.getUriComponentsBuilder().queryParam("status", systemStatus.getCode());
        }
        return client.getForObject(SystemStatusResponse[].class);
    }
}
