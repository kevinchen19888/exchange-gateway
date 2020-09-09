package com.alchemy.gateway.broker.repository;

import com.alchemy.gateway.broker.entity.Account;
import com.alchemy.gateway.broker.entity.type.AccountStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long>, JpaSpecificationExecutor<Account> {

    Optional<Account> findByAccountIdAndStatusLessThan(String accountId, AccountStatusEnum status);

    List<Account> findByStatusLessThan(AccountStatusEnum status);

    Optional<Account> findByAccountId(String accountId);
}