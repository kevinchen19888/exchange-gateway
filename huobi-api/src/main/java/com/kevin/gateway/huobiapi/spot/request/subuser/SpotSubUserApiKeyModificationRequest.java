package com.kevin.gateway.huobiapi.spot.request.subuser;

import lombok.Data;

/**
 * 修改子用户API key
 */
@Data
public class SpotSubUserApiKeyModificationRequest {
    private String subUid;//子用户的uid
    private String accessKey;//access key
    private String note;//API key备注
    private String permission;    //API key权限
    private String ipAddresses;//	API key绑定的IP地址
}
