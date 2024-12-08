package com.dev.qlda.repo;

import com.dev.qlda.entity.ActionHistoryLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ActionRepo extends JpaRepository<ActionHistoryLog, String> {
    @Query("select a from ActionHistoryLog a where a.assetId = :id")
    Page<ActionHistoryLog> getAssetHistoryByPage(Pageable pageable, String id);

    @Query("select a from ActionHistoryLog a where a.assigneeId = :id")
    Page<ActionHistoryLog> getAssigneeHistoryByPage(Pageable pageable, String id);

    @Query("select a from ActionHistoryLog a where a.executorId = :id")
    Page<ActionHistoryLog> getExecutorHistoryByPage(Pageable pageable, String id);

    Optional<ActionHistoryLog> findFirstByAssetCodeOrderByActionDateDesc(String assetCode);

}
