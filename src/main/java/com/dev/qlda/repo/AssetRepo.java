package com.dev.qlda.repo;

import com.dev.qlda.entity.AssetQuantity;
import com.dev.qlda.entity.Assets;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AssetRepo extends JpaRepository<Assets, String> {
    boolean existsByCode(String assetCode);

    Assets findByCode(String assetCode);

    @Query("select a from Assets a")
    Page<Assets> getAssetsInPage(Pageable pageable);

}
