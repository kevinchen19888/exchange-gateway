package com.kevin.gateway.huobiapi.spot.response.subuser;

import com.kevin.gateway.huobiapi.spot.model.SpotSubUserAccountType;
import com.kevin.gateway.huobiapi.spot.response.SpotBaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

/**
 * 设置子用户资产转出权限
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
public class SpotSubUserTransferabilityResponse extends SpotBaseResponse {
    private List<SpotSubUserTransferability> data;

    @Data
    private static class SpotSubUserTransferability {
        private String subUid;    //子用户UID列表（支持多填，最多50个，逗号分隔）	-
        private SpotSubUserAccountType accountType;//账户类型（如不填，缺省值spot）	spot
        private boolean transferrable;    //可划转权限	true,false
        private String errCode;    //请求被拒错误码（仅在设置该subUid市场准入权限错误时返回）
        private String errMessage;//	请求被拒错误消息（仅在设置该subUid市场准入权限错误时返回）
    }
}
