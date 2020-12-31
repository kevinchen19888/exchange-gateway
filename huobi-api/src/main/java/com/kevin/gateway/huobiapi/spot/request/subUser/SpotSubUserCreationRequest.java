package com.kevin.gateway.huobiapi.spot.request.subUser;

import lombok.Data;

import java.util.List;

/**
 * 创建子账户
 */
@Data
public class SpotSubUserCreationRequest {

    private List<SpotSubUserCreation> userList;

    @Data
    private static class SpotSubUserCreation {
        private String userName;//子用户名，子用户身份的重要标识，要求火币平台内唯一	NA	6至20位字母和数字的组合，可为纯字母；若字母和数字的组合，需以字母开头；字母不区分大小写；
        private String note;//子用户备注，无唯一性要求	NA 最多20位字符，字符类型不限
    }


}
