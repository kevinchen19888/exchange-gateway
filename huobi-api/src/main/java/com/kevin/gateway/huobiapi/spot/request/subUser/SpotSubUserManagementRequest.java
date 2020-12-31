package com.kevin.gateway.huobiapi.spot.request.subUser;

import com.kevin.gateway.huobiapi.spot.model.SpotSubUserAction;
import lombok.Data;

/**
 * 冻结/解冻子用户
 */
@Data
public class SpotSubUserManagementRequest {
    private String subUid;//子用户的UID
    private SpotSubUserAction action;//操作类型	lock(冻结)，unlock(解冻)
}
