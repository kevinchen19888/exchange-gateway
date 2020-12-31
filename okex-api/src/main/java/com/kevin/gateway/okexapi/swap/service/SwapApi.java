package com.kevin.gateway.okexapi.swap.service;

import com.kevin.gateway.core.Credentials;
import com.kevin.gateway.okexapi.future.model.*;
import com.kevin.gateway.okexapi.future.model.*;
import com.kevin.gateway.okexapi.spot.model.SpotAlgoOrderStatus;
import com.kevin.gateway.okexapi.spot.model.SpotAlgoOrderType;
import com.kevin.gateway.okexapi.spot.model.SpotOrderInfoState;
import com.kevin.gateway.okexapi.swap.SwapMarketId;
import com.kevin.gateway.okexapi.swap.model.*;
import com.kevin.gateway.okexapi.swap.model.*;
import com.kevin.gateway.okexapi.swap.type.SwapFlowType;

public interface SwapApi {


    /**
     * (1)所有合约持仓信息
     *
     * @param c 用户api key 账号信息
     * @return 获取账户所有合约的持仓信息
     */
    GetSwapContractPositionResponse[] getAllSwapPosition(Credentials c);


    /**
     * (2)单个合约持仓信息
     *
     * @param c            用户api key 账号信息
     * @param instrumentId 合约id
     * @return 合约的持仓信息
     */
    GetSwapContractPositionResponse getSwapPositionByInstrumentId(Credentials c, SwapMarketId instrumentId);


    /**
     * (3)所有币种合约账户信息
     *
     * @param c 用户api key 账号信息
     * @return 合约账户信息
     */
    AllCoinSwapAccountResponse getAllSwapAccounts(Credentials c);


    /**
     * (4)获取单个币种合约的账户信息，当用户没有持仓时，保证金率为10000  限速规则：20次/2s
     *
     * @param c            用户api key 账号信息
     * @param instrumentId 合约id
     * @return 账户信息
     */
    SingleCoinSwapAccountResponse getSwapAccountByInstrumentId(Credentials c, SwapMarketId instrumentId);


    /**
     * (5)获取某个合约的杠杆倍数，持仓模式 限速规则：5次/2s
     *
     * @param c            用户api key 账号信息
     * @param instrumentId 合约id
     * @return 杠杆倍数
     */
    SwapSettingsResponse getSwapAccountsSettings(Credentials c, SwapMarketId instrumentId);


    /**
     * (6)设定某个合约的杠杆 限速规则：5次/2s
     *
     * @param c            用户api key 账号信息
     * @param instrumentId 合约id
     * @param body         参数
     * @return 杠杆倍数
     */
    SwapSettingsResponse setSwapAccountsLeverage(Credentials c, SwapMarketId instrumentId, SetSwapBodyRequest body);


    /**
     * (7)账单流水查询。帐户资产流水是指导致帐户余额增加或减少的行为。本接口能查询最近三个月的数据
     *
     * @param c            用户api key 账号信息
     * @param instrumentId 合约id
     * @param after        请求此id之前（更旧的数据）的分页内容，传的值为对应接口的ledger_id
     * @param before       请求此id之后（更旧的数据）的分页内容，传的值为对应接口的ledger_id
     * @param limit        返回数目  分页返回的结果集数量，最大为100，不填默认返回100条
     * @param type         类型
     * @return 流水列表
     */
    SwapAccountFlowResponse[] getSwapAccountsFlows(Credentials c, SwapMarketId instrumentId, Long after, Long before, Integer limit, SwapFlowType type);


    /**
     * (8)永续合约下单
     *
     * @param c    用户api key 账号信息
     * @param body 下单参数
     * @return 合约下单信息
     */
    PlaceOrderResponse placeSwapOrder(Credentials c, SwapPlaceOrderRequest body);

    /**
     * (9)永续合约批量下单
     *
     * @param c    用户api key 账号信息
     * @param body 下单参数
     * @return 返回下单应答
     */
    BatchPlaceOrderResponse batchPlaceSwapOrders(Credentials c, SwapBatchPlaceOrderRequest body);


    /**
     * (10)撤销之前下的未完成订单。 限速规则：40次/2s
     *
     * @param c            用户api key 账号信息
     * @param instrumentId 合约id
     * @param orderId      订单id
     * @return 撤单结果
     */
    CancelSwapOrderResponse cancelSwapOrder(Credentials c, SwapMarketId instrumentId, String orderId);


    /**
     * (11) 批量撤单
     * 撤销之前下的未完成订单，每个合约可批量撤10个单。
     * <p>
     * 限速规则：20次/2s （1）不同合约之间限速不累计；2）同一合约的币本位和USDT保证金之间限速不累计）
     * HTTP请求
     * POST /api/swap/v3/cancel_batch_orders/<instrument_id>
     * <p>
     * 请求示例
     * POST /api/swap/v3/cancel_batch_orders/BTC-USD-SWAP{"ids":["500723297223680","50072329722368","5007232972236801"]}
     * <p>
     * 或
     *
     * @param credentials  用户api key 账号信息
     * @param instrumentId 合约id
     * @param cancelOrders 订单参数
     * @return 返回应答
     */
    BatchCancelSwapOrderResponse cancelSwapOrders(Credentials credentials, SwapMarketId instrumentId, BatchCancelOrderRequest cancelOrders);


    /**
     * (12)修改之前下的未完成订单
     *
     * @param c            用户api key 账号信息
     * @param instrumentId 合约id
     * @param body         参数
     * @return 返回报文
     */
    AmendOrderResponse amendSwapOrder(Credentials c, SwapMarketId instrumentId, AmendOrderRequest body);


    /**
     * (13) 批量修改订单
     * 修改之前下的未完成订单，每个合约最多可批量修改10个单。
     * <p>
     * 限速规则：20次/2s
     * HTTP请求
     * POST/api/swap/v3/amend_batch_orders/<instrument_id>
     *
     * @param credentials            用户api key 账号信息
     * @param instrumentId           合约id
     * @param batchAmendOrderRequest 修改参数
     * @return 返回应答报文
     */
    BatchAmendOrderResponse batchAmendSwapOrders(Credentials credentials, SwapMarketId instrumentId, BatchAmendOrderRequest batchAmendOrderRequest);


    /**
     * (14)获取所有订单列表
     *
     * @param c            用户api key 账号信息
     * @param instrumentId 合约id
     * @param after        请求此id之前（更旧的数据）的分页内容，传的值为对应接口的order_id
     * @param before       请求此id之后（更旧的数据）的分页内容，传的值为对应接口的order_id
     * @param limit        返回数目  分页返回的结果集数量，最大为100，不填默认返回100条
     * @param state        状态
     * @return 订单列表
     */
    SwapOrderDetailListResponse getSwapOrders(Credentials c, SwapMarketId instrumentId, Long after, Long before, Integer limit, SpotOrderInfoState state);


    /**
     * (15) 获取订单信息
     * 通过订单ID获取单个订单信息。已撤销的未成交单只保留2个小时
     *
     * @param c            用户api key 账号信息
     * @param instrumentId 合约id
     * @param orderId      订单id
     * @return 返回订单详细
     */
    SwapOrderDetailResponse getSwapOrderDetail(Credentials c, SwapMarketId instrumentId, String orderId);


    /**
     * (16) 获取成交明细
     * 获取最近的成交明细列表，本接口能查询最近7天的数据。。
     *
     * @param c            用户api key 账号信息
     * @param instrumentId 合约id
     * @param orderId      订单id
     * @param after        请求此id之前（更旧的数据）的分页内容，传的值为对应接口的trade_id等
     * @param before       请求此id之后（更旧的数据）的分页内容，传的值为对应接口的trade_id等
     * @param limit        返回数目 分页返回的结果集数量，最大为100，不填默认返回100条
     * @return 成交明细列表
     */
    SwapFillsDetailResponse[] getSwapFills(Credentials c, SwapMarketId instrumentId, String orderId, Long after, Long before, Integer limit);


    /**
     * (17)获取合约挂单冻结数量
     *
     * @param c            用户api key 账号信息
     * @param instrumentId 合约id
     * @return 获取合约挂单冻结数量
     */
    SwapHoldResponse getSwapAccountsHolds(Credentials c, SwapMarketId instrumentId);


    /**
     * (18) 委托策略下单
     *
     * @param c    用户api key 账号信息
     * @param body 参数
     * @return 返回应答
     */
    SwapPlaceAlgoOrderResponse placePolicyOrder(Credentials c, SwapPlaceAlgoOrderRequest body);


    /**
     * (19) 委托策略撤单
     *
     * @param c    用户api key 账号信息
     * @param body 参数
     * @return 返回应答
     */
    SwapCancelPolicyOrderResponse cancelPolicyOrder(Credentials c, SwapCancelAlgoOrderRequest body);


    /**
     * (22)获取委托单列表
     *
     * @param c            用户api key 账号信息
     * @param instrumentId 合约id
     * @param orderType    订单类型
     * @param status       状态
     * @param algoId       id
     * @param after        请求此id之后（更新的数据）的分页内容
     * @param before       请求此id之前（更新的数据）的分页内容
     * @param limit        返回条数
     * @return 根据需要返回列表
     */
    GetSwapOrdersListResponse getSwapOrders(Credentials c, SwapMarketId instrumentId, SpotAlgoOrderType orderType, SpotAlgoOrderStatus status, String algoId, Long after, Long before, Integer limit);


    /**
     * (23)获取当前手续费费率
     * 获取您当前账户交易等级对应的手续费费率，母账户下的子账户的费率和母账户一致。每天凌晨0点更新一次
     * <p>
     * 限速规则：20次/2s
     *
     * @param c            用户api key 账号信息
     * @param category     手续费档位 1：第一档，2：第二档 ；category和instrumentId仅选填写一个参数
     * @param instrumentId 合约标的指数，e.g BTC-USD ；category和instrumentId仅选填写一个参数
     * @return 手续费率
     */
    SwapTradeFeeResponse getSwapTradeFee(Credentials c, Integer category, SwapMarketId instrumentId);


    /**
     * (24)市价全平
     *
     * @param c    用户api key 账号信息
     * @param body 全平参数
     * @return 市价全平结果
     */
    SetSwapPositionResponse closeAllSwapPosition(Credentials c, SetSwapClosePositionRequest body);


    /**
     * (25)撤销所有平仓挂单
     *
     * @param c    用户api key 账号信息
     * @param body 取消订单参数
     * @return 返回撤单结果
     */
    SetSwapPositionResponse cancelSwapAll(Credentials c, SetSwapClosePositionRequest body);


}
