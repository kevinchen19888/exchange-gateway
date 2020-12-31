package com.kevin.gateway.huobiapi.spot.request.subUser;

import com.kevin.gateway.huobiapi.spot.model.SpotSubUserAccountType;
import com.kevin.gateway.huobiapi.spot.model.SpotSubUserActivation;
import lombok.Data;

/**
 * 设置子用户交易权限
 */
@Data
public class SpotSubUserTradableMarketRequest {
    private String subUids;//子用户UID列表（支持多填，最多50个，逗号分隔）
    private SpotSubUserAccountType accountType;    //	账户类型	isolated-margin,cross-margin
    private SpotSubUserActivation activation;    //	账户激活状态	activated,deactivated
}
