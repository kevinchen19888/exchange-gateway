package com.kevin.gateway.okexapi.fundingaccount.rest;

import com.kevin.gateway.core.Coin;
import com.kevin.gateway.core.Credentials;
import com.kevin.gateway.okexapi.base.util.AccountType;
import com.kevin.gateway.okexapi.base.util.OkexPagination;
import com.kevin.gateway.okexapi.base.util.OperationType;
import com.kevin.gateway.okexapi.fundingaccount.domain.*;
import org.springframework.lang.Nullable;

/**
 * 资金账户API
 */
public interface FundingAccountApi {
    /**
     * 获取资金账户所有资产列表
     *
     * @param credentials 凭证信息
     * @return 账户信息列表
     */
    WalletResponse[] getWallets(Credentials credentials);

    /**
     * 获取单一币种账户信息
     *
     * @param credentials 凭证信息
     * @param currency    币种，如BTC
     * @return 账户信息
     */
    WalletResponse[] getWalletInfo(Credentials credentials, Coin currency);

    /**
     * OKEx站内在资金账户、交易账户和子账户之间进行资金划转。
     *
     * @param credentials 凭证
     * @param req         请求参数封装
     * @return 资金划转结果
     */
    TransferResponse assetTransfer(Credentials credentials, AssetTransferRequest req);

    /**
     * 提币
     *
     * @param credentials 凭证
     * @param req         请求参数封装
     * @return 提币结果
     */
    WithdrawalResponse withdraw(Credentials credentials, WithdrawalRequest req);

    /**
     * 查询资金账户账单流水，可查询最近一个月的数据。
     *
     * @param credentials 凭证
     * @param currency    币种，如BTC ，不填时返回所有的账单流水
     * @param type        操作类型
     * @param pagination 分页信息
     * @return 账单流水查询结果列表
     */
    LedgerResponse[] getLedgerInfos(Credentials credentials, @Nullable Coin currency,
                                    @Nullable OperationType type, @Nullable OkexPagination pagination);

    /**
     * 获取各个币种的充值地址，包括曾使用过的老地址。
     *
     * @param credentials 凭证
     * @param currency    币种
     * @return 充值地址列表
     */
    DepositAddressResponse[] getDepositAddress(Credentials credentials, Coin currency);

    /**
     * 按照btc或法币计价单位，获取账户总资产的估值
     *
     * @param credentials       凭证
     * @param accountType       账户类型
     * @param valuationCurrency 币种类型
     * @return 资产评估值
     */
    AssetValuationResponse getAssetValuation(Credentials credentials, @Nullable AccountType accountType, @Nullable Coin valuationCurrency);

    /**
     * @param credentials    凭证
     * @param subAccountName 申请子账户的时候 给子账户设定的账户名称
     * @return 子账户信息
     */
    SubAccountResponse getSubAccountInfo(Credentials credentials, String subAccountName);

    /**
     * 查询最近所有币种的提币记录，为最近100条记录。
     *
     * @param credentials 凭证
     * @return 提币记录列表
     */
    WithdrawalHistoryResponse[] getAllWithdrawalHistory(Credentials credentials);

    /**
     * 查询单个币种的提币记录。
     *
     * @param credentials 凭证
     * @param currency    币种
     * @return 提币记录
     */
    WithdrawalHistoryResponse[] getWithdrawalHistory(Credentials credentials, Coin currency);

    /**
     * 获取所有币种的充值记录，为最近一百条数据（一年内）。
     *
     * @param credentials 凭证
     * @return 充值记录列表
     */
    DepositHistoryResponse[] getAllDepositHistory(Credentials credentials);

    /**
     * 获取单个币种的充值记录，最多可查询最近三个月的数据。
     *
     * @param credentials 凭证
     * @param currency    币种
     * @param after       请求此id之前（更旧的数据）的分页内容，传的值为对应接口的deposit_id等
     * @param before      请求此id之后（更新的数据）的分页内容，传的值为对应接口的deposit_id等
     * @param limit       分页返回的结果集数量，最大为100，不填默认返回100条
     * @return 单币种充值记录
     */
    DepositHistoryResponse[] getDepositHistory(Credentials credentials, Coin currency, @Nullable Long after, @Nullable Long before, @Nullable Integer limit);

    /**
     * 获取平台所有币种列表。
     * <br/>
     * 并非所有币种都可被用于交易。
     * 在ISO 4217标准中未被定义的币种代码可能使用的是自定义代码。
     *
     * @param credentials 凭证
     * @return 所有币种列表
     */
    CurrencyResponse[] getCurrenciesInfo(Credentials credentials);

    /**
     * 获取币种提币手续费
     *
     * @param credentials 凭证
     * @param currency 币种
     * @return 所有币种的提币手续费列表
     */
    WithdrawalFeeResponse[] getWithdrawalFees(Credentials credentials, @Nullable Coin currency);

}


