package com.alchemy.gateway.quotation.repository;

import com.alchemy.gateway.quotation.entity.Kline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KlineRepository extends JpaRepository<Kline, Long> {

}

