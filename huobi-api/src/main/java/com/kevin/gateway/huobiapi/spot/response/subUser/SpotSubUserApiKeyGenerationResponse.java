package com.kevin.gateway.huobiapi.spot.response.subUser;

import com.kevin.gateway.huobiapi.spot.response.SpotBaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 子用户API key创建
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
public class SpotSubUserApiKeyGenerationResponse extends SpotBaseResponse {
    private SpotSubUserApiKeyGeneration data;

    @Data
    private static class SpotSubUserApiKeyGeneration {
        private String note;    //API key备注
        private String accessKey;//access key
        private String secretKey;//	secret key
        private String permission;    //	API key权限
        private String ipAddresses; //	API key绑定的IP地址
    }
}
