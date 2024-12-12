package com.dev.qlda.controller;

import com.dev.qlda.repo.*;
import com.dev.qlda.request.PageRequest;
import com.dev.qlda.response.WrapResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/page")
@CrossOrigin(origins = "http://localhost:3010")
public class PageListController {
    private final UserRepo userRepo;
    private final AssetRepo assetRepo;
    private final TransferRepo transferRepo;
    private final LiquidationRepo liquidationRepo;
    private final ActionRepo actionRepo;
    private final ProjectRepo projectRepo;
    private final ContactSupportRepo contactSupportRepo;
    private final AssetQuantityRepo assetQuantityRepo;

    @PostMapping("/users")
    public WrapResponse<?> getUsers(@RequestBody PageRequest request){
        return WrapResponse.ok(userRepo.getUsersInPage(request.getPageable()));
    }

    @PostMapping("/assets")
    public WrapResponse<?> assets(@RequestBody PageRequest request){
        return WrapResponse.ok(assetRepo.getAssetsInPage(request.getPageable()));
    }

    @PostMapping("/transfers")
    public WrapResponse<?> transfers(@RequestBody PageRequest request){
        return WrapResponse.ok(transferRepo.getTransfersInPage(request.getPageable()));
    }

    @PostMapping("/liquidation")
    public WrapResponse<?> liquidations(@RequestBody PageRequest request){
        return WrapResponse.ok(liquidationRepo.getLiquidationInPage(request.getPageable()));
    }

    @PostMapping("/asset-history/{id}")
    public WrapResponse<?> assetHistory(@PathVariable String id, @RequestBody PageRequest request){
        return WrapResponse.ok(actionRepo.getAssetHistoryByPage(request.getPageable(), id));
    }

    @PostMapping("/assginee-history/{id}")
    public WrapResponse<?> assigneeHistory(@PathVariable String id, @RequestBody PageRequest request){
        return WrapResponse.ok(actionRepo.getAssigneeHistoryByPage(request.getPageable(), id));
    }

    @PostMapping("/executor-history/{id}")
    public WrapResponse<?> executorHistory(@PathVariable String id, @RequestBody PageRequest request){
        return WrapResponse.ok(actionRepo.getExecutorHistoryByPage(request.getPageable(), id));
    }

    @PostMapping("/project")
    public WrapResponse<?> projects(@RequestBody PageRequest request){
        return WrapResponse.ok(projectRepo.getProjectsByPage(request.getPageable()));
    }

    @PostMapping("/contacts")
    public WrapResponse<?> contacts(@RequestBody PageRequest request){
        return WrapResponse.ok(contactSupportRepo.getContactInPage(request.getPageable()));
    }

    @PostMapping("/asset-quantity-by-id/{id}")
    public WrapResponse<?> assetQuantityById(@PathVariable String id, @RequestBody PageRequest request){
        return WrapResponse.ok(assetQuantityRepo.getByAssetIdInPage(id, request.getPageable()));
    }

    @PostMapping("/asset-quantity-by-department/{department}")
    public WrapResponse<?> assetQuantityByDepartment(@PathVariable String department, @RequestBody PageRequest request){
        return WrapResponse.ok(assetQuantityRepo.getByDepartmentInPage(department, request.getPageable()));
    }
}
