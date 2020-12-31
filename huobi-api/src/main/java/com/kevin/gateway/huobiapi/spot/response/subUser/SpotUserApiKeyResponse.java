package com.kevin.gateway.huobiapi.spot.response.subUser;

import com.kevin.gateway.huobiapi.spot.model.SpotUserState;
import com.kevin.gateway.huobiapi.spot.response.SpotBaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

/**
 * 母子用户API key信息查询
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
public class SpotUserApiKeyResponse extends SpotBaseResponse {
    private List<SpotUserApiKey> data;

    @Data
    private static class SpotUserApiKey {
        private String accessKey;//access key
        private String note;//API key备注
        private String permission;    //API key权限
        private String ipAddresses;//	API key绑定的IP地址
        private int validDays;    //	API key剩余有效天数	若为-1，则表示永久有效
        private SpotUserState status;//	API key当前状态	normal(正常)，expired(已过期)
        private long createTime;//API key创建时间
        private long updateTime;//API key最近一次修改时间
    }
}
