package com.alchemy.gateway.broker.repository;

import com.alchemy.gateway.broker.entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface TradeRepository extends JpaRepository<Trade, Long>, JpaSpecificationExecutor<Trade> {

    Optional<Trade> findByExchangeOrderIdAndExchangeTradeId(String exchangeOrderId,String exchangeTradeId);

}