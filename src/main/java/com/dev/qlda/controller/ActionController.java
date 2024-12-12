package com.dev.qlda.controller;

import com.dev.qlda.entity.ActionHistoryLog;
import com.dev.qlda.entity.Assets;
import com.dev.qlda.entity.Users;
import com.dev.qlda.repo.ActionRepo;
import com.dev.qlda.repo.AssetRepo;
import com.dev.qlda.request.CreateActionRequest;
import com.dev.qlda.response.WrapResponse;
import com.dev.qlda.utils.CurrentUser;
import com.dev.qlda.utils.MappingUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/api/action")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ActionController {
    private final ActionRepo actionRepo;
    private final AssetRepo assetRepo;

    @PostMapping("/create")
    public WrapResponse<?> create(@RequestBody CreateActionRequest request) {
        Assets assets = assetRepo.findByCode(request.getAssetCode());
        if (assets == null) {
            return WrapResponse.error("tai san khong ton tai");
        }
        Users user = CurrentUser.get();
        if (user == null) {
            return WrapResponse.error("nguoi dung khong ton tai");
        }

        ActionHistoryLog action = MappingUtils.mapObject(request, ActionHistoryLog.class);
        action.setAssetId(assets.getId());
        action.setAssetName(assets.getName());
        action.setAssetType(assets.getType());
        action.setExecutor(user.getFullName());
        action.setExecutorId(user.getId());
        action.setAssigneeId(request.getAssigneeId());
        action.setAssigneeName(request.getAssignee());
        action.setId(UUID.randomUUID().toString());
        action.setOriginalActionId(action.getId());
        return WrapResponse.ok("thuc hien thao tac thanh cong", actionRepo.save(action));
    }

    @PostMapping("/execute-action/{id}")
    public WrapResponse<?> acceptOr(@PathVariable String id, @RequestBody CreateActionRequest request){
        ActionHistoryLog originalAction = actionRepo.findById(id).orElse(null);
        if (originalAction == null) {
            return WrapResponse.error("lich su khong ton tai");
        }
        Assets assets = assetRepo.findByCode(request.getAssetCode());
        if (assets == null) {
            return WrapResponse.error("tai san khong ton tai");
        }
        ActionHistoryLog action = MappingUtils.mapObject(request, ActionHistoryLog.class);
        action.setAssetId(assets.getId());
        action.setAssetName(originalAction.getAssetName());
        action.setAssetType(originalAction.getAssetType());
        action.setExecutor(Objects.requireNonNull(CurrentUser.get()).getFullName());
        action.setAssigneeId(request.getAssigneeId());
        action.setAssigneeName(request.getAssignee());
        action.setId(UUID.randomUUID().toString());
        action.setOriginalActionId(originalAction.getId());;
        return WrapResponse.ok("thuc hien thao tac thanh cong", actionRepo.save(action));
    }

    @GetMapping("/{id}")
    public WrapResponse<?> getById(@PathVariable String id){
        return WrapResponse.ok(actionRepo.findById(id).orElse(null));
    }

    @GetMapping("/latest-by-asset-code/{assetCode}")
    public WrapResponse<?> getByAssetCode(@PathVariable String assetCode){
        return WrapResponse.ok(actionRepo.findFirstByAssetCodeOrderByActionDateDesc(assetCode).orElse(null));
    }
}
