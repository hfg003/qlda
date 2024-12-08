package com.dev.qlda.controller;

import com.dev.qlda.entity.AssetQuantity;
import com.dev.qlda.entity.Assets;
import com.dev.qlda.entity.Transfers;
import com.dev.qlda.function.ImportAssetsFunc;
import com.dev.qlda.repo.AssetQuantityRepo;
import com.dev.qlda.repo.AssetRepo;
import com.dev.qlda.request.CreateAssetRequest;
import com.dev.qlda.response.WrapResponse;
import com.dev.qlda.utils.CodeGenerators;
import com.dev.qlda.utils.MappingUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/assets")
public class AssetController {

    private final AssetRepo assetRepo;
    private final CodeGenerators codeGenerators;
    private final ImportAssetsFunc importAssetsFunc;
    private final AssetQuantityRepo assetQuantityRepo;

    @PostMapping("/create")
    public WrapResponse<?> createAssets(@RequestBody CreateAssetRequest request) {
        Assets assets = MappingUtils.mapObject(request, Assets.class);
        assets.setId(UUID.randomUUID().toString());
        assets.setCode(codeGenerators.generateAssetCode(request.getType()));
        Assets savedRecord = assetRepo.save(assets);
        createAssetQuantity(Collections.singletonList(savedRecord));
        return WrapResponse.ok("Them thong tin tai san thanh cong", savedRecord);
    }

    @PostMapping("/import")
    public WrapResponse<?> importAsset(@RequestPart("file") MultipartFile file) {
        try {
            return importAssetsFunc.importAssets(file.getBytes());
        } catch (IOException e) {
            return WrapResponse.builder()
                    .isSuccess(false)
                    .status(HttpStatus.BAD_REQUEST)
                    .message("Có lỗi xảy ra trong quá trình import")
                    .build();
        }
    }

    @PostMapping("/update/{id}")
    public WrapResponse<?> updateAssets(@PathVariable String id, @RequestBody CreateAssetRequest request) {
        Assets assets = assetRepo.findById(id).orElse(null);
        if (assets == null) {
            return WrapResponse.error("Tai san khong ton tai");
        }
        assets.setName(request.getName());
        assets.setDescription(request.getDescription());
        assets.setType(request.getType());
        assets.setManufacturer(request.getManufacturer());
        assets.setValue(request.getValue());
        assets.setQuantity(request.getQuantity());
        assets.setBoughtDate(request.getBoughtDate());
        return WrapResponse.ok("Cap nhat thong tin tai san thanh cong", assetRepo.save(assets));
    }

    @PostMapping("/delete/{id}")
    public WrapResponse<?> deleteAssets(@PathVariable String id) {
        Assets assets = assetRepo.findById(id).orElse(null);
        if (assets == null) {
            return WrapResponse.error("Tai san khong ton tai");
        }
        assetRepo.delete(assets);
        return WrapResponse.ok("Xoa tai san thanh cong");
    }

    @GetMapping("/{id}")
    public WrapResponse<?> get(@PathVariable String id) {
        Assets assets = assetRepo.findById(id).orElse(null);
        if (assets == null) {
            return WrapResponse.error("Tai san khong ton tai");
        }
        return WrapResponse.ok(assets);
    }

    public void createAssetQuantity(List<Assets> assets){
        List<AssetQuantity> saveList = new ArrayList<>();
        for (String department : List.of("NHA K", "NHA B", "NHA HIEU BO", "NHA C", "NHA D", "NHA V", "SAN VAN DONG", "KHO")){
            for (Assets asset : assets){
                saveList.add(AssetQuantity.builder()
                                .id(UUID.randomUUID().toString())
                                .assetId(asset.getId())
                                .assetCode(asset.getCode())
                                .assetName(asset.getName())
                                .assetType(asset.getType())
                                .manufacturer(asset.getManufacturer())
                                .quantity(0L)
                        .location(department)
                        .build());
            }
        }
        assetQuantityRepo.saveAll(saveList);
    }
}
