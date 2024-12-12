package com.dev.qlda.controller;

import com.dev.qlda.entity.Roles;
import com.dev.qlda.repo.RoleRepo;
import com.dev.qlda.response.WrapResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/public/api")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class PublicController {
    private final RoleRepo roleRepo;

    @GetMapping("/roles")
    public WrapResponse<?> getListRoles(){
        return WrapResponse.ok(roleRepo.findAll().stream().map(Roles::getName).toList());
    }

    @PostMapping("/department")
    public WrapResponse<?> getListDepartments(@RequestBody Roles role){
        if ("ADMIN".equals(role.getName())) {
            return WrapResponse.ok(List.of("IT"));
        }
        if ("BUILDINGMANAGER".equals(role.getName())) {
            return WrapResponse.ok(List.of("NHA K", "NHA B", "NHA HIEU BO", "NHA C", "NHA D", "NHA V", "SAN VAN DONG"));
        }
        if ("STOREKEEPER".equals(role.getName())) {
            return WrapResponse.ok(List.of("NHA KHO"));
        }
        if ("REPAIRMANAGER".equals(role.getName())) {
            return WrapResponse.ok(List.of("PHONG QUAN LY SUA CHUA"));
        }
        return WrapResponse.ok(Collections.emptyList());
    }
}
