package com.alchemy.gateway.broker.repository;

import com.alchemy.gateway.broker.entity.AccountAsset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface AccountAssetRepository extends JpaRepository<AccountAsset, Long>, JpaSpecificationExecutor<AccountAsset> {
    Optional<AccountAsset> findByAccountIdAndCoin(Long accountId, String coin);
}