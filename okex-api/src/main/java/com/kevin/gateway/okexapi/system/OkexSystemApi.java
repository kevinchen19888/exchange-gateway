package com.kevin.gateway.okexapi.system;

import com.kevin.gateway.okexapi.system.response.SystemStatus;
import com.kevin.gateway.okexapi.system.response.SystemStatusResponse;
import org.springframework.lang.Nullable;

public interface OkexSystemApi {

    SystemStatusResponse[] getStatuses(@Nullable SystemStatus systemStatus);
}
