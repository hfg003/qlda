package com.dev.qlda.controller;

import com.dev.qlda.entity.Users;
import com.dev.qlda.repo.UserRepo;
import com.dev.qlda.response.WrapResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/create")
    public WrapResponse<?> create(@RequestBody Users user) {
        if (StringUtils.isBlank(user.getFullName()) || StringUtils.isBlank(user.getUsername()) ||
                StringUtils.isBlank(user.getPassword()) || StringUtils.isBlank(user.getRole())) {
            return WrapResponse.error("Thông tin người dùng không được để trống");
        }
        user.setId(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return WrapResponse.ok(userRepo.save(user));
    }

    @PostMapping("/update/{id}")
    public WrapResponse<?> update(@PathVariable String id, @RequestBody Users updatedUser) {
        Users user = userRepo.findById(id).orElse(null);
        if (user == null) {
            return WrapResponse.error("Người dùng không tồn tại: " + id);
        }
        if (StringUtils.isBlank(updatedUser.getFullName()) || StringUtils.isBlank(updatedUser.getUsername()) ||
                StringUtils.isBlank(updatedUser.getPassword()) || StringUtils.isBlank(updatedUser.getRole())) {
            return WrapResponse.error("Thông tin người dùng không được để trống");
        }
        user.setFullName(updatedUser.getFullName());
        user.setUsername(updatedUser.getUsername());
        user.setPassword(updatedUser.getPassword());
        user.setRole(updatedUser.getRole());
        user.setDepartment(updatedUser.getDepartment());
        return WrapResponse.ok("Cập nhật người dùng thành công", userRepo.save(user));
    }

    @PostMapping("/delete/{id}")
    public WrapResponse<?> delete(@PathVariable String id) {
        Users user = userRepo.findById(id).orElse(null);
        if (user == null) {
            return WrapResponse.error("Người dùng không tồn tại: " + id);
        }
        userRepo.delete(user);
        return WrapResponse.ok("Xóa người dùng thành công", id);
    }

    @GetMapping("/{id}")
    public WrapResponse<?> get(@PathVariable String id) {
        Users user = userRepo.findById(id).orElse(null);
        if (user == null) {
            return WrapResponse.error("Người dùng không tồn tại: " + id);
        }
        return WrapResponse.ok(user);
    }

    @GetMapping("/{username}")
    WrapResponse<?> getByUsername(@PathVariable String username) {
        return WrapResponse.ok(userRepo.findByUsername(username));
    }
}
