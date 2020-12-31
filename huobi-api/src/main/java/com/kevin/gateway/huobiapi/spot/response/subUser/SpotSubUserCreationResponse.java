package com.kevin.gateway.huobiapi.spot.response.subUser;

import com.kevin.gateway.huobiapi.spot.response.SpotBaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 创建子账户
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SpotSubUserCreationResponse extends SpotBaseResponse {
    private List<SpotSubUserCreation> data;

    @Data
    private static class SpotSubUserCreation {
        private String userName;//子用户名
        private String note;//子用户备注（仅对有备注的子用户有效）
        private String uid;//子用户UID（仅对创建成功的子用户有效）
        private String errCode;//创建失败错误码（仅对创建失败的子用户有效）
        private String errMessage;//创建失败错误原因（仅对创建失败的子用户有效）
    }
}
