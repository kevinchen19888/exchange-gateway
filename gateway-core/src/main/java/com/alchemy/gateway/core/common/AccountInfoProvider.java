package com.alchemy.gateway.core.common;

/**
 * 账户信息提供器
 */
public interface AccountInfoProvider {
    /**
     * 通过平台账户id 获取账户信息
     *
     * @param accountId 平台账户id
     * @return 平台账户信息
     */
    AccountInfo getAccountInfo(Long accountId);
}
