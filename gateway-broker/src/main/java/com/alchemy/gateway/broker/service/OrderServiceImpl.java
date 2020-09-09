package com.alchemy.gateway.broker.service;

import com.alchemy.gateway.broker.entity.Account;
import com.alchemy.gateway.broker.entity.Order;
import com.alchemy.gateway.broker.entity.Trade;
import com.alchemy.gateway.broker.exception.OrderCheckException;
import com.alchemy.gateway.broker.exception.OrderNotExistException;
import com.alchemy.gateway.broker.repository.OrderRepository;
import com.alchemy.gateway.broker.repository.TradeRepository;
import com.alchemy.gateway.core.ExchangeApi;
import com.alchemy.gateway.core.ExchangeManager;
import com.alchemy.gateway.core.common.AccountInfo;
import com.alchemy.gateway.core.common.CoinPair;
import com.alchemy.gateway.core.common.Credentials;
import com.alchemy.gateway.core.common.Market;
import com.alchemy.gateway.core.order.*;
import com.alchemy.gateway.core.order.tracker.ThreadedOrderTracker;
import com.alchemy.gateway.market.MarketManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * describe:订单服务
 *
 * @author zoulingwei
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderStatusCallback, ApplicationRunner, OrderMessageHandler {

    @Value("${gateway.order.node}")
    private static String node;

    private final OrderRepository orderRepository;
    private final TradeRepository tradeRepository;
    private final ExchangeManager exchangeManager;
    private final AccountServiceImpl accountServiceImpl;
    private final OrderMessageSender orderMessageSender;
    private final ThreadedOrderTracker threadedOrderTracker;
    private final MarketManager marketManager;

    @Autowired
    public OrderServiceImpl(OrderRepository repository,
                            TradeRepository tradeRepository,
                            AccountServiceImpl accountServiceImpl,
                            OrderMessageSender orderMessageSender,
                            ThreadedOrderTracker threadedOrderTracker,
                            ExchangeManager exchangeManager,
                            MarketManager marketManager,
                            MarketLoaderService marketLoaderService) {
        this.orderRepository = repository;
        this.tradeRepository = tradeRepository;
        this.accountServiceImpl = accountServiceImpl;
        this.orderMessageSender = orderMessageSender;
        this.threadedOrderTracker = threadedOrderTracker;
        this.marketManager = marketManager;
        this.exchangeManager = exchangeManager;
        marketManager.load(marketLoaderService);
    }

    @Transactional
    public void orderSubmit(String accountId, String eName, OrderType type, BigDecimal price, BigDecimal amount, OrderSide side,
                            Long orderId, CoinPair coinPair, BigDecimal stopPrice, OperatorType operatorType) {
        Order order = new Order();
        try {
            Account account = accountServiceImpl.findByAccountId(accountId);
            order.setMineOrderId(orderId);
            order.setExchangeOrderId(null);
            order.setAccountId(account.getId());
            order.setPrice(price != null ? price.stripTrailingZeros() : null);
            order.setAmount(amount != null ? amount.stripTrailingZeros() : null);
            order.setSide(side);
            order.setSymbol(coinPair.toSymbol());
            order.setStopPrice(stopPrice != null ? stopPrice.stripTrailingZeros() : null);
            order.setType(type);
            order.setState(OrderState.SUBMITTING);
            orderRepository.save(order);

            if (price != null) {
                Market market = marketManager.getMarket(eName, coinPair);
                Integer pricePrecision = market.getPricePrecision();

                int precision = getPrecision(price);
                if (precision > pricePrecision) {
                    order.setState(OrderState.SUBMIT_FAILED);
                    orderRepository.save(order);
                    orderMessageSender.sendPlaceOrderMessage(order.getMineOrderId(), eName, 1, "价格精度错误");
                    return;
                }
            }


            Account byAccuntId = accountServiceImpl.findByAccountId(accountId);
            AccountInfo accountInfo = accountServiceImpl.getAccountInfo(byAccuntId.getId());

            Credentials credentials = accountInfo.getCredentials(); // 获取 凭证

            OrderApi orderApi = exchangeManager.getAPI(accountInfo.getExchangeName()).getOrderApi();//获取api

            OrderManager orderManager = new OrderManagerImpl(orderApi, threadedOrderTracker);

            OrderRequest orderRequest = new OrderRequest(
                    order.getMineOrderId(),
                    order.getAccountId(),
                    Market.spotMarket(coinPair),
                    type,
                    order.getPrice(),
                    order.getStopPrice(),
                    order.getAmount(),
                    side,
                    accountInfo.getExchangeName(),
                    operatorType
            );

            boolean b = orderApi.checkOrderDefined(orderRequest);//校验下单参数合法性
            if (!b) {
                throw new OrderCheckException("下单参数校验异常");
            }
            OrderVo orderVo = orderManager.placeOrder(credentials, orderRequest, this);
            order.setExchangeOrderId(orderVo.getExchangeOrderId());
            order.setState(OrderState.CREATED);
            orderRepository.save(order);

        } catch (Exception e) {
            log.error("下单异常:" + e.getMessage());
            order.setState(OrderState.SUBMIT_FAILED);
            orderRepository.save(order);
            orderMessageSender.sendPlaceOrderMessage(order.getMineOrderId(), eName, 1, e.getMessage());
            return;
        }
        orderMessageSender.sendPlaceOrderMessage(order.getMineOrderId(), eName, 0, "");

    }

    /**
     * 获取数值小数点后有效数字长度
     *
     * @param price 数值
     * @return 长度
     */
    private int getPrecision(BigDecimal price) {
        BigDecimal bigDecimal = price.stripTrailingZeros();
        String str = bigDecimal.toPlainString();
        int i = str.indexOf(".");
        if (i == -1) {
            return 0;
        }
        String substring = str.substring(str.indexOf(".") + 1, str.length());
        return substring.length();
    }

    public void orderCancel(String accountId, String exchangeName, Long orderId) {
        Account byAccuntId = accountServiceImpl.findByAccountId(accountId);
        AccountInfo accountInfo = accountServiceImpl.getAccountInfo(byAccuntId.getId());
        Credentials credentials = accountInfo.getCredentials(); // 获取 凭证
        OrderApi orderApi = exchangeManager.getAPI(exchangeName).getOrderApi();//获取api

        Optional<Order> byId = orderRepository.findByMineOrderId(orderId);
        if (!byId.isPresent()) {
            orderMessageSender.sendCancelOrderMessage(orderId, exchangeName, 1, "订单不存在");
            return;
        }
        if (byId.get().getExchangeOrderId() == null) {
            orderMessageSender.sendCancelOrderMessage(orderId, exchangeName, 1, "交易所订单未提交");
            return;
        }
        try {
            orderApi.cancelOrder(credentials, String.valueOf(byId.get().getExchangeOrderId()), CoinPair.fromSymbol(byId.get().getSymbol()), byId.get().getType());
        } catch (Exception e) {
            orderMessageSender.sendCancelOrderMessage(orderId, exchangeName, 1, e.getMessage());
            return;
        }
        byId.get().setState(OrderState.CANCELLED);
        orderRepository.save(byId.get());
        orderMessageSender.sendCancelOrderMessage(orderId, exchangeName, 0, "");
    }


    @Override
    public void stateChanged(OrderVo orderVo) {
        //回调方法
        Optional<Order> byEOrderId = orderRepository.findByExchangeOrderIdAndMineOrderId(orderVo.getExchangeOrderId(), orderVo.getMineOrderId());
        if (!byEOrderId.isPresent()) {
            log.error("进行中订单变化时数据库未查到委托单:orderVo平台订单id:" + orderVo.getMineOrderId() + ";交易所订单id:" + orderVo.getExchangeOrderId());
            return;
        }
        //更新订单最新数据
        if(orderVo.getFinishedAtAt()!=null){
            byEOrderId.get().setFinishedAt(orderVo.getFinishedAtAt());
        }
        byEOrderId.get().setState(orderVo.getOrderState());
        byEOrderId.get().setFinishedFee(orderVo.getFinishedFees() != null ? orderVo.getFinishedFees() : BigDecimal.ZERO);
        byEOrderId.get().setFinishedVolume(orderVo.getFinishedVolume());
        byEOrderId.get().setFinishedAmount(orderVo.getFinishedAmount());
        byEOrderId.get().setRebate(orderVo.getRebate());
        byEOrderId.get().setRebateCoin(orderVo.getRebateCoin());
        if (byEOrderId.get().getState().getIntValue() < OrderState.CREATED.getIntValue()) {
            //发送订单完结信息
            orderMessageSender.sendOrderStateEnd(
                    byEOrderId.get().getMineOrderId(),
                    byEOrderId.get().getState(),
                    byEOrderId.get().getFinishedAmount(),
                    byEOrderId.get().getFinishedVolume(),
                    byEOrderId.get().getFinishedFee(),
                    byEOrderId.get().getRebate(),
                    byEOrderId.get().getRebateCoin());
        }
        orderRepository.save(byEOrderId.get());//储存mysql
    }

    @Override
    public void insertTrade(List<TradeVo> tradeVos, Long mineOrderId) {
        Optional<Order> byMineOrderId = orderRepository.findByMineOrderId(mineOrderId);
        if (!byMineOrderId.isPresent()) {
            throw new OrderNotExistException("插入成交记录时,委托订单不存在:mineOrderId=" + mineOrderId);
        }
        tradeVos.forEach(tradeVo -> {
            Optional<Trade> trade = tradeRepository.findByExchangeOrderIdAndExchangeTradeId(tradeVo.getExchangeOrderId(), tradeVo.getExchangeTradeId());
            if (!trade.isPresent()) {//待优化
                Trade entity = new Trade();
                entity.setExchangeName(tradeVo.getExchangeName());
                entity.setExchangeOrderId(tradeVo.getExchangeOrderId());
                entity.setExchangeTradeId(tradeVo.getExchangeTradeId());
                entity.setFeeDeductAmount(tradeVo.getFeeDeductAmount());
                entity.setFeeDeductCoin(tradeVo.getFeeDeductCoin());
                entity.setFilledAmount(tradeVo.getFilledAmount());
                entity.setFilledFee(tradeVo.getFilledFee());
                entity.setFilledFeeCoin(tradeVo.getFilledFeeCoin());
                entity.setOrderId(byMineOrderId.get().getId());
                entity.setPrice(tradeVo.getPrice());
                entity.setRole(tradeVo.getRole());
                entity.setCreatedAt(tradeVo.getCreatedAt());
                tradeRepository.save(entity);
                orderMessageSender.sendOrderRecord(entity, mineOrderId);
            }
        });
    }

    @Override
    public void run(ApplicationArguments args) {
        Map<String, ExchangeApi> all = exchangeManager.getAll();//获取所有交易所api
        Set<String> strings = all.keySet();
        for (String key : strings) {
            List<Market> markets = marketManager.getMarkets(key);
            all.get(key).getOrderApi().initOrderLimit(markets);//初始化每个交易所订单限制
        }

        List<Order> orders = orderRepository.findAllByStateGreaterThan(OrderState.FILLED);
        orders.forEach(order -> {
            AccountInfo accountInfo = accountServiceImpl.getAccountInfo(order.getAccountId());
            OrderApi orderApi = exchangeManager.getAPI(accountInfo.getExchangeName()).getOrderApi();//获取api
            OrderVo orderVo = orderApi.getOrder(accountInfo.getCredentials(), String.valueOf(order.getExchangeOrderId()), CoinPair.fromSymbol(order.getSymbol()), order.getType());
            orderVo.setOrderState(order.getState().name().toLowerCase());
            orderVo.setFinishedAmount(order.getFinishedAmount());
            orderVo.setFinishedVolume(order.getFinishedVolume());
            orderVo.setFinishedFees(order.getFinishedFee());
            orderVo.setExchangeName(accountInfo.getExchangeName());
            orderVo.setMineOrderId(order.getMineOrderId());
            threadedOrderTracker.trackOrder(accountInfo.getCredentials(), orderVo, this);
        });
    }
}
