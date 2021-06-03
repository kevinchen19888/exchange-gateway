package com.kevin.gateway.huobiapi.spot.response.subuser;

import com.kevin.gateway.huobiapi.spot.model.SpotUserState;
import com.kevin.gateway.huobiapi.spot.response.SpotBaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 获取特定子用户的用户状态
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
public class SpotSubUserStateResponse extends SpotBaseResponse {
    private SpotSubUserState data;

    @Data
    public static class SpotSubUserState {
        private String uid;//子用户UID
        private SpotUserState userState;    //子用户状态	lock(已冻结)，normal(正常)
    }
}
