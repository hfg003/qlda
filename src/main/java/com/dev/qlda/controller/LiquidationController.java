package com.dev.qlda.controller;

import com.dev.qlda.entity.Assets;
import com.dev.qlda.entity.Liquidation;
import com.dev.qlda.repo.AssetRepo;
import com.dev.qlda.repo.LiquidationRepo;
import com.dev.qlda.request.CreateLiquidationRequest;
import com.dev.qlda.response.WrapResponse;
import com.dev.qlda.utils.MappingUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/liquidation")
@CrossOrigin(origins = "http://localhost:3010")
public class LiquidationController {
    private final LiquidationRepo liquidationRepo;
    private final AssetRepo assetRepo;

    @PostMapping("/create")
    public WrapResponse<?> create(@RequestBody CreateLiquidationRequest request) {
        Assets asset = assetRepo.findByCode(request.getAssetCode());
        if (asset == null) {
            return WrapResponse.error("Tai san khong ton tai");
        }
        Liquidation liquidation = MappingUtils.mapObject(request, Liquidation.class);
        liquidation.setId(UUID.randomUUID().toString());
        liquidation.setAssetName(request.getAssetName());
        liquidation.setAssetType(asset.getType());
        return WrapResponse.ok("Them thong tin thanh ly tai san thanh cong", liquidationRepo.save(liquidation));
    }

    @PostMapping("/update/{id}")
    public WrapResponse<?> create(@PathVariable String id, @RequestBody CreateLiquidationRequest request) {
        Liquidation liquidation = liquidationRepo.findById(id).orElse(null);
        if (liquidation == null) {
            return WrapResponse.error("Thong tin thanhg ly khong ton tai");
        }
        Assets asset = assetRepo.findByCode(request.getAssetCode());
        if (asset == null) {
            return WrapResponse.error("Tai san khong ton tai");
        }
        liquidation.setLiquidationPrice(request.getLiquidationPrice());
        liquidation.setLiquidationDate(request.getLiquidationDate());
        liquidation.setAssetName(asset.getName());
        liquidation.setAssetCode(request.getAssetCode());
        liquidation.setQuantity(request.getQuantity());
        liquidation.setAssetType(asset.getType());
        return WrapResponse.ok("Them thong tin thanh ly tai san thanh cong", liquidationRepo.save(liquidation));
    }

    @PostMapping("/delete/{id}")
    public WrapResponse<?> create(@PathVariable String id) {
        Liquidation liquidation = liquidationRepo.findById(id).orElse(null);
        if (liquidation == null) {
            return WrapResponse.error("Thong tin thanhg ly khong ton tai");
        }
        liquidationRepo.delete(liquidation);
        return WrapResponse.ok("Xoa thong tin thanh ly tai san thanh cong");
    }

    @GetMapping("/{id}")
    public WrapResponse<?> get(@PathVariable String id) {
        Liquidation liquidation = liquidationRepo.findById(id).orElse(null);
        if (liquidation == null) {
            return WrapResponse.error("Thong tin thanhg ly khong ton tai");
        }
        return WrapResponse.ok(liquidation);
    }
}
