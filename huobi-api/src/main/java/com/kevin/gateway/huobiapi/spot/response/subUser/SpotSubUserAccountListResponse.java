package com.kevin.gateway.huobiapi.spot.response.subUser;

import com.kevin.gateway.huobiapi.spot.model.SpotSubUserAccountType;
import com.kevin.gateway.huobiapi.spot.model.SpotSubUserActivation;
import com.kevin.gateway.huobiapi.spot.model.SpotUserState;
import com.kevin.gateway.huobiapi.spot.response.SpotBaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

/**
 * 获取特定子用户的账户列表
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
public class SpotSubUserAccountListResponse extends SpotBaseResponse {
    private SpotSubUserAccountListInfo data;

    @Data
    private static class SpotSubUserAccountListInfo {
        private String uid;//子用户UID
        private List<SpotSubUserAccountList> list;

        @Data
        private static class SpotSubUserAccountList {
            private SpotSubUserAccountType accountType;//账户类型	spot, isolated-margin, cross-margin, futures,swap
            private SpotSubUserActivation activation;//账户激活状态	activated, deactivated
            private boolean transferrable;    //可划转权限（仅对accountType=spot有效）	true, false
            private List<SpotSubUserAccountListAccountIds> accountIds;

            @Data
            private static class SpotSubUserAccountListAccountIds {
                private String accountId;    //	账户ID
                private SpotSubUserAccountType subType;//账户子类型（仅对accountType=isolated-margin有效）
                private SpotUserState accountStatus;//账户状态	normal, locked
            }
        }
    }
}
