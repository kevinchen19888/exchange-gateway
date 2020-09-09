package com.alchemy.gateway.broker.repository;

import com.alchemy.gateway.broker.entity.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AlertRepository extends JpaRepository<Alert, Long>, JpaSpecificationExecutor<Alert> {

}