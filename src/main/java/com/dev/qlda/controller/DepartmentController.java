package com.dev.qlda.controller;

import com.dev.qlda.entity.Departments;
import com.dev.qlda.repo.DepartmentRepo;
 import com.dev.qlda.response.WrapResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/department")
public class DepartmentController {

    private final DepartmentRepo departmentRepo;

    @PostMapping("/create")
    public WrapResponse<?> create(@RequestBody String name) {
        if (StringUtils.isBlank(name)){
            return WrapResponse.error("khong duoc de trong ten phong ban lam viec");
        }
        return WrapResponse.ok(departmentRepo.save(Departments.builder().id(UUID.randomUUID().toString()).name(name).build()));
    }

    @PostMapping("/update/{id}")
    public WrapResponse<?> update(@PathVariable String id, @RequestBody String name) {
        Departments departments = departmentRepo.findById(id).orElse(null);
        if (departments == null) {
            return WrapResponse.error("phong ban khong ton tai: " + id);
        }
        if (StringUtils.isBlank(name)){
            return WrapResponse.error("khong duoc de trong ten phong ban lam viec");
        }
        departments.setName(name);
        return WrapResponse.ok("cap nhat phong ban thanh cong", departments);
    }

    @PostMapping("/delete/{id}")
    public WrapResponse<?> delete(@PathVariable String id) {
        Departments departments = departmentRepo.findById(id).orElse(null);
        if (departments == null) {
            return WrapResponse.error("phong ban khong ton tai: " + id);
        }
        departmentRepo.delete(departments);
        return WrapResponse.ok("xoa phong ban thanh cong", id);
    }
}
