package com.kevin.gateway.okexapi.future.service;


import com.kevin.gateway.core.CoinPair;
import com.kevin.gateway.core.Credentials;


import com.kevin.gateway.okexapi.future.FutureMarketId;
import com.kevin.gateway.okexapi.future.model.*;
import com.kevin.gateway.okexapi.future.model.*;
import com.kevin.gateway.okexapi.future.type.AccountFlowType;
import com.kevin.gateway.okexapi.spot.model.SpotAlgoOrderStatus;
import com.kevin.gateway.okexapi.spot.model.SpotAlgoOrderType;
import com.kevin.gateway.okexapi.spot.model.SpotOrderInfoState;
import org.springframework.lang.Nullable;

public interface FuturesApi {


    /**
     * (1)获取 所有合约持仓信息
     *
     * @param c 用户api key 账号信息
     * @return position
     */
    AllContractPositionResponse getFuturesPosition(Credentials c);


    /**
     * (2)单个合约持仓信息   根据合约id 返回我的合约列表
     *
     * @param c            用户api key 账号信息
     * @param instrumentId
     * @return 返回我的合约列表
     */
    GetSingleContractPositionResponse getFuturesPositionByInstrumentId(Credentials c, FutureMarketId instrumentId);


    /**
     * (3)获取所有币种合约账户信息
     * 获取合约账户所有币种的账户信息。请求此接口，会在数据库遍历所有币对下的账户数据，有大量的性能消耗,请求频率较低。建议用户传币种获取账户信息信息。
     * <p>
     * 限速规则：5次/2s （根据UserID限速）
     *
     * @param c 用户api key 账号信息
     * @return 所有币种合约账户信息
     */
    CoinContractAccountResponse getFuturesAccounts(Credentials c);


    /**
     * (4)获取单个币种的合约账户信息
     *
     * @param c          用户api key 账号信息
     * @param underlying 合约id
     * @return 合约账户信息
     */
    CoinContractAccountResponseBase getFuturesAccountsByUnderlying(Credentials c, CoinPair underlying);


    /**
     * (5)获取合约币种杠杆倍数
     *
     * @param c          用户api key 账号信息
     * @param underlying 合约id
     * @return 获取合约 杠杆信息
     */
    LeverageBaseItem getFuturesAccountsLeverageByUnderlying(Credentials c, CoinPair underlying);


    /**
     * (6)设定合约币种杠杆倍数
     *
     * @param c    用户api key 账号信息
     * @param body 相关的参数
     * @return 返回合约杠杆参数
     */
    SetLeverageBodyResponseBase setFuturesAccountsUnderlyingLeverage(Credentials c, CoinPair underlying, SetLeverageBody body);


    /**
     * (7)账单流水查询。帐户资产流水是指导致帐户余额增加或减少的行为。本接口能查询最近三个月的数据
     *
     * @param c          用户api key 账号信息
     * @param underlying 合约id
     * @param after      开始ledger_id	请求此id之前（更旧的数据）的分页内容，传的值为对应接口的ledger_id
     * @param before     结束ledger_id  	请求此id之后（更新的数据）的分页内容，传的值为对应接口的ledger_id
     * @param limit      返回数目,最大100条，默认返回100条
     * @param type       类型
     * @return 流水列表
     */
    AccountFlowResponse[] getFuturesAccountsFlow(Credentials c, CoinPair underlying, @Nullable Long after, @Nullable Long before, @Nullable Integer limit, @Nullable AccountFlowType type);


    /**
     * (8)交割合约下单
     *
     * @param c    用户api key 账号信息
     * @param body 下单参数
     * @return 返回下单应答
     */
    PlaceOrderResponse placeFuturesOrder(Credentials c, FuturePlaceOrderRequest body);


    /**
     * (9)交割合约批量下单
     *
     * @param c    用户api key 账号信息
     * @param body 下单参数
     * @return 返回下单应答
     */
    BatchPlaceOrderResponse batchPlaceFuturesOrder(Credentials c, FutureBatchPlaceOrderRequest body);

    /**
     * (10)取消订单
     *
     * @param c            用户api key 账号信息
     * @param instrumentId 合约id
     * @param orderId      订单id
     * @return 取消订单 返回的参数
     */
    CancelOrderResponse cancelFuturesOrderByInstrumentId(Credentials c, FutureMarketId instrumentId, String orderId);


    /**
     * (10)批量取消订单
     *
     * @param c            用户api key 账号信息
     * @param instrumentId 合约id
     * @param body         订单id 列表
     * @return 取消订单 返回的参数
     */
    BatchCancelFutureOrderResponse cancelFutureOrders(Credentials c, FutureMarketId instrumentId, BatchCancelOrderRequest body);

    /**
     * (12)修改订单
     *
     * @param c            用户api key 账号信息
     * @param instrumentId 合约id
     * @param body         订单参数
     * @return 返回修改应答参数
     */
    AmendOrderResponse amendFutureOrder(Credentials c, FutureMarketId instrumentId, AmendOrderRequest body);


    /**
     * (13)批量修改订单
     *
     * @param c            用户api key 账号信息
     * @param instrumentId 合约id
     * @param body         订单参数
     * @return 返回修改应答参数
     */
    BatchAmendOrderResponse batchAmendFutureOrders(Credentials c, FutureMarketId instrumentId, BatchAmendOrderRequest body);

    /**
     * (14)获取订单列表
     *
     * @param c            用户api key 账号信息
     * @param instrumentId 合约id
     * @param after        请求此id之前(更旧的数据)的分页内容，传的值为对应接口的order_id
     * @param before       请求此id之后(更旧的数据)的分页内容，传的值为对应接口的order_id
     * @param limit        返回条目
     * @param state        状态
     * @return 返回订单列表
     */
    OrderListResponse getFutureOrdersByState(Credentials c, FutureMarketId instrumentId, @Nullable String after, @Nullable String before, @Nullable Integer limit, SpotOrderInfoState state);


    /**
     * (15)获取订单信息
     * 通过订单ID获取单个订单信息。已撤销的未成交单只保留2个小时。
     * <p>
     * 限速规则：60次/2s （根据underlying，分别限速）
     *
     * @param c            用户api key 账号信息
     * @param instrumentId 合约id
     * @param orderId      订单id
     * @return 订单信息
     */
    OrderDetailResponse getFutureOrderDetail(Credentials c, FutureMarketId instrumentId, String orderId);


    /**
     * (16)获取成交明细
     *
     * @param c            用户api key 账号信息
     * @param instrumentId 合约id
     * @param orderId      订单id
     * @param after        请求此id之前（更旧的数据）的分页内容，传的值为对应接口的trade_id
     * @param before       请求此id之后（更旧的数据）的分页内容，传的值为对应接口的trade_id
     * @param limit        返回数目
     * @return 根据条件查询 已经完成的合约订单
     */
    FillsDetailResponse[] getFutureFills(Credentials c, FutureMarketId instrumentId, @Nullable String orderId, @Nullable Integer after, @Nullable Integer before, @Nullable Integer limit);


    /**
     * (17)设置合约币种账户模式参数
     *
     * @param c    用户api key 账号信息
     * @param body 设置合约币种账户模式参数
     * @return 合约币种账户模式
     */
    SetMarginModeResponse setFuturesAccountsMarginMode(Credentials c, SetMarginModeRequest body);


    /**
     * (18)市价全平
     *
     * @param c    用户api key 账号信息
     * @param body 全平参数
     * @return 市价全平结果
     */
    SetClosePositionResponse closeAllFuturesPosition(Credentials c, SetClosePositionRequest body);


    /**
     * (19)撤销所有平仓挂单
     *
     * @param c    用户api key 账号信息
     * @param body 取消订单参数
     * @return 返回撤单结果
     */
    CancelAllResponse cancelFuturesAll(Credentials c, CancelAllRequest body);


    /**
     * (20)获取合约挂单冻结数量
     *
     * @param c            用户api key 账号信息
     * @param instrumentId 合约id
     * @return 获取合约挂单冻结数量
     */
    HoldResponse getFuturesAccountsHolds(Credentials c, FutureMarketId instrumentId);


    /**
     * (21) 委托策略下单
     *
     * @param c    用户api key 账号信息
     * @param body 参数
     * @return 返回应答
     */
    FuturePlacePolicyOrderResponse placePolicyOrder(Credentials c, FuturePlaceAlgoOrderRequest body);

    /**
     * (21) 委托策略撤单
     *
     * @param c    用户api key 账号信息
     * @param body 参数
     * @return 返回应答
     */
    CancelFuturePolicyOrderResponse cancelPolicyOrder(Credentials c, FutureCancelAlgoOrderRequest body);

    /**
     * (22)获取委托单列表
     *
     * @param c            用户api key 账号信息
     * @param instrumentId 合约id
     * @param orderType    订单类型
     * @param status       状态
     * @param algoId       id
     * @param after        开始时间
     * @param before       结束时间
     * @param limit        返回条数
     * @return 根据需要返回列表
     */
    OrdersBaseItem[] getFuturesOrderByAlgoInstrumentId(Credentials c, FutureMarketId instrumentId, SpotAlgoOrderType orderType, SpotAlgoOrderStatus status, String algoId, @Nullable Integer after, @Nullable Integer before, @Nullable Integer limit);


    /**
     * (23)获取当前手续费费率
     * 获取您当前账户交易等级对应的手续费费率，母账户下的子账户的费率和母账户一致。每天凌晨0点更新一次
     * <p>
     * 限速规则：20次/2s
     *
     * @param c          用户api key 账号信息
     * @param category   手续费档位 1：第一档，2：第二档 ；category和underlying仅选填写一个参数
     * @param underlying 合约标的指数，e.g BTC-USD ；category和underlying仅选填写一个参数
     * @return 手续费率
     */
    TradeFeeResponse getFuturesTradeFee(Credentials c, String category, CoinPair underlying);


    /**
     * (24)增加/减少某逐仓仓位的保证金
     *
     * @param c    用户api key 账号信息
     * @param body 请求参数
     * @return 返回的应答
     */
    UpdateMarginResponse updateFuturesPositionMargin(Credentials c, UpdateMarginRequest body);


    /**
     * https://www..com/docs/zh/#futures-margin
     * (25) 设置逐仓自动增加保证金
     *
     * @param c    用户api key 账号信息
     * @param body 设置逐仓自动增加保证金参数
     * @return 自动增加保证金应答
     */
    SetAutoMarginResponse setFuturesAccountsAutoMargin(Credentials c, SetAutoMarginRequest body);


}
