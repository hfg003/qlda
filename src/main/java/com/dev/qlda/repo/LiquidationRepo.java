package com.dev.qlda.repo;

import com.dev.qlda.entity.Liquidation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LiquidationRepo extends JpaRepository<Liquidation, String> {
    @Query("select l from Liquidation l")
    Page<Liquidation> getLiquidationInPage(Pageable pageable);
}
