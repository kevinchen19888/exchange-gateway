package com.kevin.gateway.huobiapi.spot.response.subUser;

import com.kevin.gateway.huobiapi.spot.model.SpotUserState;
import com.kevin.gateway.huobiapi.spot.response.SpotBaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 冻结/解冻子用户
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
public class SpotSubUserManagementResponse extends SpotBaseResponse {
    private SpotSubUserManagement data;

    @Data
    private static class SpotSubUserManagement {
        private String subUid;//子用户UID
        private SpotUserState userState;    //子用户状态	lock(已冻结)，normal(正常)
    }
}
