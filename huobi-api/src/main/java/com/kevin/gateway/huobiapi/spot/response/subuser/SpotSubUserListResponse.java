package com.kevin.gateway.huobiapi.spot.response.subuser;

import com.kevin.gateway.huobiapi.spot.model.SpotUserState;
import com.kevin.gateway.huobiapi.spot.response.SpotBaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

/**
 * 子账户列表
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
public class SpotSubUserListResponse extends SpotBaseResponse {
    private List<SpotSubUserList> data;

    @Data
    private static class SpotSubUserList {
        private long uid;//子用户UID
        private SpotUserState userState;//子用户状态	lock, normal
    }
}
