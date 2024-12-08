package com.dev.qlda.repo;

import com.dev.qlda.entity.AssetQuantity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AssetQuantityRepo extends JpaRepository<AssetQuantity, String> {
    @Query("select a from AssetQuantity a where a.assetCode = :assetCode and (a.location = :currentPosition or a.location = :originalPosition)")
    List<AssetQuantity> findAllByAssetCodeAndDepartment(@Param("assetCode") String assetCode, @Param("currentPosition") String currentPosition, @Param("originalPosition") String originalPosition);

    @Query("select a from AssetQuantity a where a.assetId = :id")
    Page<AssetQuantity> getByAssetIdInPage(String id, Pageable pageable);

    @Query("select a from AssetQuantity a where a.location = :department")
    Page<AssetQuantity> getByDepartmentInPage(String department, Pageable pageable);
}
