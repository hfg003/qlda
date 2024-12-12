package com.dev.qlda.controller;

import com.dev.qlda.entity.AssetQuantity;
import com.dev.qlda.repo.AssetQuantityRepo;
import com.dev.qlda.response.WrapResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/asset/quantity")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AssetQuantityController {
    private final AssetQuantityRepo assetQuantityRepo;

//    public WrapResponse<?> intAssetQuantityByDepartment(@RequestBody List<String> departments){
//        List<AssetQuantity> assetQuantities = new ArrayList<>();
//        for (String department : departments) {
//            AssetQuantity newItem = AssetQuantity.builder()
//                    .id(UUID.randomUUID().toString())
//                    .
//                    .build();
//        }
//    }
}
