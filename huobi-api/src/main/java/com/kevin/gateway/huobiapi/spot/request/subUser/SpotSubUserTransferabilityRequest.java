package com.kevin.gateway.huobiapi.spot.request.subUser;

import com.kevin.gateway.huobiapi.spot.model.SpotAccountType;
import lombok.Data;

/**
 * 设置子用户资产转出权限
 */
@Data
public class SpotSubUserTransferabilityRequest {
    private String subUids;    //子用户UID列表（支持多填，最多50个，逗号分隔）	-
    private SpotAccountType accountType;//账户类型（如不填，缺省值spot）	spot
    private boolean transferrable;    //可划转权限	true,false
}
