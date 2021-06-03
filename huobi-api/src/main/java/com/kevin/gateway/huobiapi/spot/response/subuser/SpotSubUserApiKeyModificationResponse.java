package com.kevin.gateway.huobiapi.spot.response.subuser;

import com.kevin.gateway.huobiapi.spot.response.SpotBaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 修改子用户API key
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
public class SpotSubUserApiKeyModificationResponse extends SpotBaseResponse {
    private SpotSubUserApiKeyModification data;

    @Data
    private static class SpotSubUserApiKeyModification {
        private String note;    //	API key备注
        private String permission;//API key权限
        private String ipAddresses;    //	API key绑定的IP地址
    }
}
