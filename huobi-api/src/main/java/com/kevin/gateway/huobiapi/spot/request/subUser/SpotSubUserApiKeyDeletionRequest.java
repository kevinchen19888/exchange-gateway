package com.kevin.gateway.huobiapi.spot.request.subUser;

import lombok.Data;

/**
 * 删除子用户api key
 */
@Data
public class SpotSubUserApiKeyDeletionRequest {
    private String subUid;//子用户的uid
    private String accessKey;//access key
}
