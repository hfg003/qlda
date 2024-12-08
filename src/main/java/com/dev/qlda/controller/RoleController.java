package com.dev.qlda.controller;

import com.dev.qlda.entity.Roles;
import com.dev.qlda.repo.RoleRepo;
import com.dev.qlda.response.WrapResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/role")
public class RoleController {

    private final RoleRepo roleRepo;

    @PostMapping("/create")
    public WrapResponse<?> create(@RequestBody String name) {
        if (StringUtils.isBlank(name)){
            return WrapResponse.error("khong duoc de trong ten role");
        }
        return WrapResponse.ok(roleRepo.save(Roles.builder().id(UUID.randomUUID().toString()).name(name).build()));
    }

    @PostMapping("/update/{id}")
    public WrapResponse<?> update(@PathVariable String id, @RequestBody String name) {
        Roles role = roleRepo.findById(id).orElse(null);
        if (role == null) {
            return WrapResponse.error("role khong ton tai: " + id);
        }
        if (StringUtils.isBlank(name)){
            return WrapResponse.error("khong duoc de trong ten role");
        }
        role.setName(name);
        return WrapResponse.ok("cap nhat role thanh cong", role);
    }

    @PostMapping("/delete/{id}")
    public WrapResponse<?> delete(@PathVariable String id) {
        Roles role = roleRepo.findById(id).orElse(null);
        if (role == null) {
            return WrapResponse.error("role khong ton tai: " + id);
        }
        roleRepo.delete(role);
        return WrapResponse.ok("xoa role thanh cong", id);
    }
}
