package com.alchemy.gateway.broker.repository;

import com.alchemy.gateway.broker.entity.AssetTransfer;
import com.alchemy.gateway.core.wallet.DepositWithdrawType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface AssetTransferRepository extends JpaRepository<AssetTransfer, Long>, JpaSpecificationExecutor<AssetTransfer> {

    Optional<AssetTransfer> findByAccountIdAndExchangeRecordIdAndType(Long accountId, String exchangeRecordId, String type);
}