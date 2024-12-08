package com.dev.qlda.controller;

import com.dev.qlda.entity.Users;
import com.dev.qlda.repo.UserRepo;
import com.dev.qlda.response.WrapResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/dev/api")
@RequiredArgsConstructor
public class DevAPi {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/create-user")
    public WrapResponse<?> createUser(@RequestBody Users user) {
        user.setId(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return WrapResponse.ok(userRepo.save(user));
    }
}
