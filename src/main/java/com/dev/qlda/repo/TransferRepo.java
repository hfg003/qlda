package com.dev.qlda.repo;

import com.dev.qlda.entity.Transfers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TransferRepo extends JpaRepository<Transfers, String> {
    @Query("select t from Transfers  t")
    Page<Transfers> getTransfersInPage(Pageable pageable);
}
