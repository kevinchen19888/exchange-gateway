package com.kevin.gateway.okexapi.index;

import com.kevin.gateway.core.CoinPair;
import com.kevin.gateway.okexapi.index.response.OkexSearchIndexConstituentsResponse;

public interface OkexIndexApi {

    /**
     * 公共-获取指数成分
     * 获取指数成分。此接口为公共接口，不需要身份验证。
     * <p>
     * 限速规则：20次/2s
     *
     * @param coinPair 币对信息
     * @return 指数成分信息
     */
    OkexSearchIndexConstituentsResponse searchIndexConstituents(CoinPair coinPair);
}
