package com.kevin.gateway.huobiapi.spot.response.subuser;

import com.kevin.gateway.huobiapi.spot.model.SpotSubUserAccountType;
import com.kevin.gateway.huobiapi.spot.model.SpotSubUserActivation;
import com.kevin.gateway.huobiapi.spot.response.SpotBaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

/**
 * 设置子用户交易权限
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
public class SpotSubUserTradableMarketResponse extends SpotBaseResponse {
    private List<SpotSubUserTradableMarket> data;

    @Data
    private static class SpotSubUserTradableMarket {
        private String subUid;    //子用户UID
        private SpotSubUserAccountType accountType;    //账户类型	isolated-margin,cross-margin
        private SpotSubUserActivation activation;    //账户激活状态	activated,deactivated
        private String errCode;    //请求被拒错误码（仅在设置该subUid市场准入权限错误时返回）
        private String errMessage;//	请求被拒错误消息（仅在设置该subUid市场准入权限错误时返回）
    }

}
