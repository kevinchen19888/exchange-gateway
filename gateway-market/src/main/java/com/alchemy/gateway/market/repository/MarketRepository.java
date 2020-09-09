package com.alchemy.gateway.market.repository;

import com.alchemy.gateway.market.entity.Market;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.List;

@Repository
public interface MarketRepository extends JpaRepository<Market, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Market> findAllByNodeId(String nodeId);

    @Query(value = "SELECT * from market where market.node_id is null LIMIT ?1,?2", nativeQuery = true)
    List<Market> findAllByNodeIdIsNull(Integer min, Integer mix);

    @Query(value = "SELECT * from market where market.node_id = ?1 LIMIT ?2,?3", nativeQuery = true)
    List<Market> findAllByNodeId(String nodeId, Integer min, Integer mix);

    List<Market> findAllByMarketType(String marketType);
}
