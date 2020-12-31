package com.kevin.gateway.huobiapi.spot.request.subUser;

import lombok.Data;

/**
 * 子用户API key创建
 */
@Data
public class SpotSubUserApiKeyGenerationRequest {
    private String otpToken;//	母用户的谷歌验证码，母用户须在官网页面启用GA二次验证	NA	6个字符，纯数字
    private String subUid;//子用户UID	NA
    private String note;    //	API key备注	NA	最多255位字符，字符类型不限
    private String permission;//	API key权限	NA	取值范围：readOnly、trade，其中readOnly必传，trade选传，两个间用半角逗号分隔。
    private String ipAddresses;    //API key绑定的IPv4/IPv6主机地址或IPv4网络地址	NA	最多绑定20个，多个IP地址用半角逗号分隔，如：192.168.1.1,202.106.96.0/24。如果未绑定任何IP地址，API key有效期仅为90天。
}
