package com.dev.qlda.controller;

import com.dev.qlda.entity.AssetQuantity;
import com.dev.qlda.entity.Assets;
import com.dev.qlda.entity.Liquidation;
import com.dev.qlda.entity.Transfers;
import com.dev.qlda.repo.AssetQuantityRepo;
import com.dev.qlda.repo.AssetRepo;
import com.dev.qlda.repo.TransferRepo;
import com.dev.qlda.request.TransferAssetRequest;
import com.dev.qlda.response.WrapResponse;
import com.dev.qlda.utils.MappingUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transfer")
public class TransferController {

    private final TransferRepo transferRepo;
    private final AssetRepo assetRepo;
    private final AssetQuantityRepo assetQuantityRepo;

    @PostMapping("/")
    public WrapResponse<?> transferAsset(@RequestBody TransferAssetRequest request){
        if (!assetRepo.existsByCode(request.getAssetCode())){
            return WrapResponse.error("Asset has not exists");
        }
        Transfers transfers = MappingUtils.mapObject(request, Transfers.class);
        transfers.setId(UUID.randomUUID().toString());
        Transfers savedRecord = transferRepo.save(transfers);
        updateAssetQuantity(savedRecord);
        return WrapResponse.ok("Chuyen giao tai khoan thanh cong", savedRecord);
    }

    @GetMapping("/{id}")
    public WrapResponse<?> get(@PathVariable String id) {
        Transfers transfers = transferRepo.findById(id).orElse(null);
        if (transfers == null) {
            return WrapResponse.error("Thong tin chuyen giao khong ton tai");
        }
        return WrapResponse.ok(transfers);
    }

    public void updateAssetQuantity(Transfers transfers){
        List<AssetQuantity> saveList = assetQuantityRepo.findAllByAssetCodeAndDepartment(transfers.getAssetCode(), transfers.getCurrentPosition(), transfers.getOriginalPosition());
        if (CollectionUtils.isEmpty(saveList)) return;
        for (AssetQuantity assetQuantity : saveList){
            if (assetQuantity.getLocation().equals(transfers.getCurrentPosition())){
                assetQuantity.setQuantity(assetQuantity.getQuantity() + transfers.getQuantity());
            }
            if (assetQuantity.getLocation().equals(transfers.getOriginalPosition())){
                assetQuantity.setQuantity(assetQuantity.getQuantity() - transfers.getQuantity());
            }
        }
        assetQuantityRepo.saveAll(saveList);
    }
}
