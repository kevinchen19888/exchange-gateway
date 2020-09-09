package com.alchemy.gateway.broker.repository;

import com.alchemy.gateway.broker.entity.DepositWithdraw;
import com.alchemy.gateway.core.wallet.DepositWithdrawType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface DepositWithdrawRepository extends JpaRepository<DepositWithdraw, Long>, JpaSpecificationExecutor<DepositWithdraw> {

    Optional<DepositWithdraw> findByAccountIdAndExchangeRecordIdAndType(Long accountId, String exchangeRecordId, DepositWithdrawType type);
}