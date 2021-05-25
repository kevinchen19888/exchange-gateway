package com.kevin.gateway.huobiapi.spot;

import com.kevin.gateway.core.Credentials;
import com.kevin.gateway.core.FiatCoin;
import com.kevin.gateway.huobiapi.base.util.CandleInterval;
import com.kevin.gateway.huobiapi.spot.model.SpotAccountType;
import com.kevin.gateway.huobiapi.spot.model.SpotDepthType;
import com.kevin.gateway.huobiapi.spot.model.SpotTransactType;
import com.kevin.gateway.huobiapi.spot.request.*;
import com.kevin.gateway.huobiapi.spot.response.SpotBatchOrderResponse;
import com.kevin.gateway.huobiapi.spot.response.SpotOrderResponse;
import com.kevin.gateway.huobiapi.spot.response.account.SpotAccountHistoryResponse;
import com.kevin.gateway.huobiapi.spot.response.account.SpotAccountLedgerResponse;
import com.kevin.gateway.huobiapi.spot.response.account.SpotFuturesTransferResponse;
import com.kevin.gateway.huobiapi.spot.vo.*;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;
import java.util.List;

public interface SpotApi {

    /**
     * 获取当前市场状态
     * 此节点返回当前最新市场状态。
     * 状态枚举值包括: 1 - 正常（可下单可撤单），2 - 挂起（不可下单不可撤单），3 - 仅撤单（不可下单可撤单）。
     * 挂起原因枚举值包括: 2 - 紧急维护，3 - 计划维护。
     *
     * @return 获取当前市场状态
     */
    SpotMarketStatusVo searchMarketStatus();

    /**
     * 获取所有交易对
     * 此接口返回所有火币全球站支持的交易对。
     *
     * @return 获取所有交易对
     */
    List<SpotSymbolsVo> searchCommonSymbols();

    /**
     * 获取所有币种
     * 此接口返回所有火币全球站支持的币种。
     *
     * @return 获取所有币种
     */
    List<SpotCoin> searchCommonCurrencys();

    /**
     * K 线数据（蜡烛图）
     * 此接口返回历史K线数据
     *
     * @param marketId      币对
     * @param period        时间周期
     * @param spotApiWindow 查询窗口
     * @return K 线数据（蜡烛图）
     */
    List<SpotKlineVo> searchKline(SpotMarketId marketId, CandleInterval period, @Nullable SpotApiWindow spotApiWindow);

    /**
     * 聚合行情（Ticker）
     * 此接口获取ticker信息同时提供最近24小时的交易聚合信息。
     *
     * @param marketId 币对
     * @return 聚合行情（Ticker）
     */
    SpotTickerVo searchTicker(SpotMarketId marketId);

    /**
     * 所有交易对的最新 Tickers
     *
     * @return 交易对的最新 Tickers
     */
    List<SpotLatestTickersForAllPairsVo> searchLatestTickersForAllPairs();

    /**
     * 市场深度数据
     * 此接口返回指定交易对的当前市场深度数据。
     *
     * @param marketId 币对
     * @param depth    返回深度的数量
     * @param type     深度的价格聚合度
     * @return 市场深度数据
     */
    SpotDepthVo searchDepth(SpotMarketId marketId, @Nullable BigDecimal depth, SpotDepthType type);

    /**
     * 最近市场成交记录
     * 此接口返回指定交易对最新的一个交易记录。
     *
     * @param marketId 币对
     * @return 最近市场成交记录
     */
    List<SpotTradeVo> searchTrade(SpotMarketId marketId);

    /**
     * 获得近期交易记录
     * 此接口返回指定交易对近期的所有交易记录。
     *
     * @param marketId      币对
     * @param spotApiWindow 查询窗口
     * @return 近期交易记录
     */
    List<SpotHistoryTradeVo> searchHistoryTrade(SpotMarketId marketId, @Nullable SpotApiWindow spotApiWindow);

    /**
     * 最近24小时行情数据
     *
     * @param marketId 币对
     * @return 最近24小时行情数据
     */
    SpotLast24hMarketSummaryVo searchMarketDetail(SpotMarketId marketId);

    /**
     * 获取杠杆ETP实时净值
     *
     * @param marketId 币对
     * @return 获取杠杆ETP实时净值
     */
    SpotRealTimeNavVo searchMarketEtp(SpotMarketId marketId);

    /**
     * 账户信息
     * API Key 权限：读取
     * 限频值（NEW）：100次/2s
     * <p>
     * 查询当前用户的所有账户 ID account-id 及其相关信息
     *
     * @param credentials 账户信息
     * @return 当前用户的所有账户 ID account-id 及其相关信息
     */
    List<SpotAccountsVo> searchAccountInfo(Credentials credentials);

    /**
     * 账户余额
     * API Key 权限：读取
     * 限频值（NEW）：100次/2s
     * <p>
     * 查询指定账户的余额，支持以下账户：
     * <p>
     * spot：现货账户， margin：逐仓杠杆账户，otc：OTC 账户，point：点卡账户，super-margin：全仓杠杆账户, investment: C2C杠杆借出账户, borrow: C2C杠杆借入账户
     *
     * @param credentials 账户信息
     * @param accountId   账户id
     * @return 账户余额
     */
    SpotAccountBalanceVo searchAccountBalance(Credentials credentials, long accountId);

    /**
     * 获取账户资产估值
     * API Key 权限：读取
     * <p>
     * 限频值（NEW）：100次/2s
     * <p>
     * 按照BTC或法币计价单位，获取指定账户的总资产估值。
     *
     * @param credentials       账户信息
     * @param type              账户类型
     * @param valuationCurrency 资产估值法币，即资产按哪个法币为单位进行估值。可选法币有：BTC、CNY、USD、JPY、KRW、GBP、TRY、EUR、RUB、VND、HKD、TWD、MYR、SGD、AED、SAR （大小写敏感）
     * @param subUid            子用户的 UID，若不填，则返回API key所属用户的账户资产估值
     * @return 账户资产估值
     */
    SpotAccountAssetValuationVo searchAccountAssetValuation(Credentials credentials, SpotAccountType type,
                                                            @Nullable FiatCoin valuationCurrency, @Nullable String subUid);

    /**
     * 资产划转
     * API Key 权限：交易
     * <p>
     * 该节点为母用户和子用户进行资产划转的通用接口。
     * <p>
     * 母用户和子用户均支持的功能包括：
     * 1、币币账户与逐仓杠杠账户之间的划转；
     * 2、逐仓杠杠不同账户间相同币种的直接划转，如逐仓杠杠BTC/USDT账户和ETH/USDT账户，相同币种USDT可直接划转；
     * <p>
     * 仅母用户支持的功能包括：
     * 1、母用户币币账户与子用户币币账户间的划转；
     * 2、不同子用户币币账户间划转；
     * <p>
     * 仅子用户支持的功能包括：
     * 1、子用户币币账户向母用户下的其他子用户币币账户划转，此权限默认关闭，需母用户授权。授权接口为 POST /v2/sub-user/transferability；
     * 2、子用户币币账户向母用户币币账户划转；
     *
     * @param credentials 账户信息
     * @param request     请求参数
     * @return 资产划转
     */
    SpotAccountTransferVo accountTransfer(Credentials credentials, SpotAccountTransferRequest request);

    /**
     * 账户流水
     * API Key 权限：读取
     * 限频值（NEW）：5次/2s
     * <p>
     * 该节点基于用户账户ID返回账户流水。
     *
     * @param credentials   账户信息
     * @param accountId     账户id
     * @param marketId      币对
     * @param types         变动类型
     * @param spotApiWindow 查询窗口
     * @return 账户流水
     */
    SpotAccountHistoryResponse searchAccountHistory(Credentials credentials, long accountId, @Nullable SpotMarketId marketId,
                                                    @Nullable List<SpotTransactType> types, @Nullable SpotApiWindow spotApiWindow);

    /**
     * 财务流水
     * API Key 权限：读取
     * <p>
     * 该节点基于用户账户ID返回财务流水。
     * 一期上线暂时仅支持划转流水的查询（“transactType” = “transfer”）。
     * 通过“startTime”/“endTime”框定的查询窗口最大为10天，意即，通过单次查询可检索的范围最大为10天。
     * 该查询窗口可在最近180天范围内平移，意即，通过多次平移窗口查询，最多可检索到过往180天的记录。
     *
     * @param credentials   账户信息
     * @param accountId     账户id
     * @param marketId      币对
     * @param types         变动类型
     * @param spotApiWindow 查询窗口
     * @return 财务流水
     */
    SpotAccountLedgerResponse searchAccountLedger(Credentials credentials, long accountId, @Nullable SpotMarketId marketId,
                                                  @Nullable List<SpotTransactType> types, @Nullable SpotApiWindow spotApiWindow);

    /**
     * 币币现货账户与合约账户划转
     * API Key 权限：交易
     * <p>
     * 此接口用户币币现货账户与合约账户之间的资产划转。
     * <p>
     * 从现货现货账户转至合约账户，类型为pro-to-futures; 从合约账户转至现货账户，类型为futures-to-pro
     *
     * @param credentials 账户信息
     * @param request     请求参数
     * @return 划转结果
     */
    SpotFuturesTransferResponse futuresTransfer(Credentials credentials, SpotFuturesTransferRequest request);

    /**
     * 点卡余额查询
     * 此节点既可查询到“不限时”点卡的余额，也可查询到“限时”点卡的余额、分组ID、及各组有效期。
     * 此节点仅可查询到限时/不限时点卡的余额，不可查询到其它币种余额。
     * 母用户可查询母用户或子用户点卡余额。
     * 点卡兑换仅可通过官网页面或APP完成。
     * <p>
     * API Key 权限：读取
     * 限频值：2次/秒
     * 子用户可调用
     *
     * @param credentials 账户信息
     * @param subUserId   子用户UID（仅对母用户查询子用户点卡余额场景有效）
     * @return 点卡余额
     */
    SpotPointAccountVo searchPointAccount(Credentials credentials, @Nullable Long subUserId);

    /**
     * 点卡划转
     * 此节点既可划转“不限时”点卡，也可划转“限时”点卡。
     * 此节点仅支持不限时/限时点卡的划转，不支持其它币种的划转。
     * 此节点仅支持母子用户点卡（point）账户间划转。
     * 如果登录用户为母用户，该节点支持双向划转，即母向子划转和子向母划转。
     * 如果登录用户为子用户，该节点仅支持单向划转，即子向母划转。
     * 母用户将限时点卡从子用户转回时应先行查询子用户点卡的groupId。
     * <p>
     * API Key 权限：交易
     * 限频值：2次/秒
     * 子用户可调用
     *
     * @param credentials 账户信息
     * @param request     请求参数
     * @return 点卡划转
     */
    SpotPointTransferVo pointTransfer(Credentials credentials, SpotPointTransferRequest request);

    /**
     * 批量下单
     *
     * @param credentials 凭证
     * @param orders      订单列表
     * @return 结果
     */
    SpotBatchOrderResponse batchOrder(Credentials credentials, List<SpotBatchOrderVo> orders);

    /** 下单
     * @param credentials 凭证
     * @param order 订单
     * @return 结果
     */
    SpotOrderResponse order(Credentials credentials, OrderRequest order);
}
