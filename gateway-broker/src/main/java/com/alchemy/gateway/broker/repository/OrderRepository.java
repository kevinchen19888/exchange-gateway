package com.alchemy.gateway.broker.repository;

import com.alchemy.gateway.broker.entity.Order;
import com.alchemy.gateway.core.order.OrderState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    Optional<Order> findByExchangeOrderIdAndMineOrderId(String exchangeOrderId,Long mineOrderId);

    Optional<Order> findByMineOrderId(Long mineOrderId);

    List<Order> findAllByStateGreaterThan(OrderState state);

    Optional<Order> findByExchangeOrderIdAndAccountId(String exchangeOrderId, Long accountId);
}