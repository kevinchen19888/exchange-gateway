package com.alchemy.gateway.broker.repository;

import com.alchemy.gateway.broker.entity.AssetCursor;
import com.alchemy.gateway.broker.entity.type.AssetCursorType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface AssetCursorRepository extends JpaRepository<AssetCursor, Long>, JpaSpecificationExecutor<AssetCursor> {

    List<AssetCursor> findByAccountIdAndTypeOrderByIdDesc(Long accountId, AssetCursorType type);
}