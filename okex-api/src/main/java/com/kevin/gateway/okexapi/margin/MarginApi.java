package com.kevin.gateway.okexapi.margin;

import com.kevin.gateway.core.Credentials;
import com.kevin.gateway.okexapi.base.util.OkexPagination;
import com.kevin.gateway.okexapi.margin.model.MarginBillsType;
import com.kevin.gateway.okexapi.margin.model.MarginLoanStatus;
import com.kevin.gateway.okexapi.margin.model.MarginOrderInfoState;
import com.kevin.gateway.okexapi.margin.request.MarginCancelOrderListRequest;
import com.kevin.gateway.okexapi.margin.request.MarginLoanRequest;
import com.kevin.gateway.okexapi.margin.request.MarginPlaceOrderRequest;
import com.kevin.gateway.okexapi.margin.request.MarginRepaymentRequest;
import com.kevin.gateway.okexapi.margin.response.*;
import com.kevin.gateway.okexapi.margin.response.*;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;
import java.util.List;

public interface MarginApi {

    /**
     * 币币杠杆账户信息
     * 获取币币杠杆账户资产列表，查询各币种的余额、冻结和可用等信息。
     * <p>
     * 限速规则：20次/2s
     *
     * @param credentials 账户信息
     * @return 币币杠杆账户信息
     */
    MarginAccountsResponse[] searchMarginAccounts(Credentials credentials);

    /**
     * 单一币对账户信息
     * 获取币币杠杆某币对账户的余额、冻结和可用等信息。
     * <p>
     * 限速规则：20次/2s
     *
     * @param credentials 账户信息
     * @param marketId    币对
     * @return 单一币对账户信息
     */
    MarginAccountsResponse searchMarginAccount(Credentials credentials, MarginMarketId marketId);

    /**
     * 账单流水查询
     * 列出杠杆帐户资产流水。帐户资产流水是指导致帐户余额增加或减少的行为。流水会分页，并且按时间倒序排序和存储，最新的排在最前面。
     * 请参阅分页部分以获取第一页之后的其他纪录。 本接口能查询最近3个月的数据。
     * <p>
     * 限速规则：10次/2s
     *
     * @param credentials 账户信息
     * @param marketId    币对
     * @param pagination  分页信息
     * @param type        账单类型
     * @return 账单流水查询
     */
    MarginBillsDetailsResponse[] searchBillsDetails(Credentials credentials, MarginMarketId marketId, @Nullable OkexPagination pagination, @Nullable MarginBillsType type);

    /**
     * 杠杆配置信息
     * 获取币币杠杆账户的借币配置信息，包括当前最大可借、借币利率、最大杠杆倍数。
     * <p>
     * 限速规则：20次/2s
     *
     * @param credentials 账户信息
     * @return 杠杆配置信息
     */
    MarginAccountSettingsResponse[] searchMarginAccountSettings(Credentials credentials);

    /**
     * 某个杠杆配置信息
     * 获取某个币币杠杆账户的借币配置信息，包括当前最大可借、借币利率、最大杠杆倍数。
     * <p>
     * 限速规则：20次/2s
     *
     * @param credentials 账户信息
     * @param marketId    币对
     * @return 杠杆配置信息
     */
    MarginAccountSettingsResponse[] searchMarginAccountSetting(Credentials credentials, MarginMarketId marketId);

    /**
     * 获取借币记录
     * 获取币币杠杆帐户的借币记录。这个请求支持分页，并且按时间倒序排序和存储，最新的排在最前面。请参阅分页部分以获取第一页之后的其他纪录。
     * <p>
     * 限速规则：20次/2s
     *
     * @param credentials 账户信息
     * @param pagination  分页信息
     * @param status      状态
     * @return 借币记录
     */
    MarginGetLoanHistoryResponse[] searchLoanHistory(Credentials credentials, @Nullable OkexPagination pagination, @Nullable MarginLoanStatus status);

    /**
     * 某币对借币记录
     * 获取币币杠杆帐户某币对的借币记录。这个请求支持分页，并且按时间倒序排序和存储，最新的排在最前面。请参阅分页部分以获取第一页之后的其他纪录。
     * <p>
     * 限速规则：20次/2s
     *
     * @param credentials 账户信息
     * @param marketId    币对
     * @param pagination  分页信息
     * @param status      状态
     * @return 借币记录
     */
    MarginGetLoanHistoryResponse[] searchLoanHistory(Credentials credentials, MarginMarketId marketId, @Nullable OkexPagination pagination, @Nullable MarginLoanStatus status);

    /**
     * 借币
     * 在某个币币杠杆账户里进行借币。
     * <p>
     * 限速规则：100次/2s
     *
     * @param credentials 账户信息
     * @param request     请求参数
     * @return 借币结果
     */
    MarginLoanResponse loan(Credentials credentials, MarginLoanRequest request);

    /**
     * 还币
     * 在某个币币杠杆账户里进行还币。
     * <p>
     * 限速规则：100次/2s
     *
     * @param credentials 账户信息
     * @param request     请求参数
     * @return 还币结果
     */
    MarginRepaymentResponse repayment(Credentials credentials, MarginRepaymentRequest request);

    /**
     * 下单
     * OKEx API提供limit和market和高级限价委托等下单模式。只有当您的账户有足够的资金才能下单。
     * 一旦下单，您的账户资金将在订单生命周期内被冻结。被冻结的资金以及数量取决于订单指定的类型和参数。
     * <p>
     * 限速规则：100次/2s （不同币对之间限速不累计）
     *
     * @param credentials 账户信息
     * @param request     请求参数
     * @return 下单结果
     */
    MarginPlaceOrderResponse placeOrder(Credentials credentials, MarginPlaceOrderRequest request);

    /**
     * 批量下单
     * 下指定币对的多个订单（每次只能下最多4个币对且每个币对可批量下10个单）
     * <p>
     * 限速规则：50次/2s （不同币对之间限速累计）
     *
     * @param credentials 账户信息
     * @param items       订单信息
     * @return 订单结果
     */
    MarginPlaceOrderMapResponse addOrderList(Credentials credentials, List<MarginPlaceOrderRequest> items);

    /**
     * 撤销指定订单
     * 撤销之前下的未完成订单。
     * <p>
     * 限速规则：100次/2s （不同币对之间限速不累计）
     *
     * @param credentials 账户信息
     * @param marketId    币对
     * @param clientOid   指定订单id
     * @param orderId     订单id
     * @return 撤销指定订单结果
     */
    MarginPlaceOrderResponse cancelOrder(Credentials credentials, @Nullable String orderId, @Nullable String clientOid, MarginMarketId marketId);

    /**
     * 批量撤销订单
     * 撤销指定的某一种或多种币对的未完成订单（每次只能下最多4个币对且每个币对可批量下10个单）。
     * <p>
     * 限速规则：50次/2s （不同币对之间限速累计）
     *
     * @param credentials 账户信息
     * @param request     撤销订单信息
     * @return 撤销结果
     */
    MarginPlaceOrderMapResponse cancelOrderList(Credentials credentials, List<MarginCancelOrderListRequest> request);

    /**
     * 获取订单列表
     * 列出您当前的订单信息（本接口能查询最近3个月订单信息）。这个请求支持分页，并且按委托时间倒序排序和存储，最新的排在最前面。
     * <p>
     * 限速规则：10次/2s
     *
     * @param credentials 账户信息
     * @param marketId    币对
     * @param pagination  分页信息
     * @param state       订单状态
     * @return 订单列表
     */
    MarginOrderInfoResponse[] searchOrderList(Credentials credentials, MarginMarketId marketId, @Nullable OkexPagination pagination, MarginOrderInfoState state);

    /**
     * 获取订单信息
     * 通过订单ID获取单个订单信息。可以获取近3个月订单信息。已撤销的未成交单只保留2个小时。
     * <p>
     * 限速规则：20次/2s
     *
     * @param credentials 账户信息
     * @param marketId    币对
     * @param clientOid   指定订单id
     * @param orderId     订单id
     * @return 获取订单信息
     */
    MarginOrderInfoResponse searchOrderInfo(Credentials credentials, @Nullable String orderId, @Nullable String clientOid, MarginMarketId marketId);

    /**
     * 获取所有未成交订单
     * 列出您当前所有的订单信息。这个请求支持分页，并且按时间倒序排序和存储，最新的排在最前面。请参阅分页部分以获取第一页之后的其他纪录。
     * <p>
     * 限速规则：20次/2s
     *
     * @param credentials 账户信息
     * @param marketId    币对
     * @param pagination  分页信息
     * @return 获取所有未成交订单
     */
    MarginOrderInfoResponse[] searchOrdersPendingList(Credentials credentials, MarginMarketId marketId, @Nullable OkexPagination pagination);

    /**
     * 获取成交明细
     * 获取最近的成交明细表。这个请求支持分页，并且按成交时间倒序排序和存储，最新的排在最前面。请参阅分页部分以获取第一页之后的其他记录。 本接口能查询最近3月的数据。
     * <p>
     * 限速规则：10次/2s
     *
     * @param credentials 账户信息
     * @param marketId    币对
     * @param orderId     订单Id
     * @param pagination  窗口信息
     * @return 成交记录明细信息
     */
    MarginTransactionDetailsResponse[] searchTransactionDetails(Credentials credentials, MarginMarketId marketId, @Nullable String orderId, @Nullable OkexPagination pagination);

    /**
     * 获取杠杆倍数
     * 获取币币杠杆账户币种杠杆倍数
     * <p>
     * 限速规则：100次/2s
     *
     * @param credentials 账户信息
     * @param marketId    币对
     * @return 获取杠杆倍数
     */
    MarginLeverageResponse searchLeverage(Credentials credentials, MarginMarketId marketId);

    /**
     * 设置杠杆倍数
     * 设置币币杠杆账户币对杠杆倍数。
     * <p>
     * 限速规则：100次/2s
     *
     * @param credentials 账户信息
     * @param leverage    杠杆倍数
     * @param marketId    币对
     * @return 设置杠杆倍数结果
     */
    MarginLeverageResponse setLeverage(Credentials credentials, BigDecimal leverage, MarginMarketId marketId);

    /**
     * 公共-获取标记价格
     * 获取现货杠杆标记价格。此接口为公共接口，不需要身份验证。
     * <p>
     * 限速规则：20次/2s
     *
     * @param marketId 币对
     * @return 获取标记价格
     */
    MarginMarkPriceResponse searchMarkPrice(MarginMarketId marketId);
}
