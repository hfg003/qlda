package com.dev.qlda.controller;

import com.dev.qlda.entity.Users;
import com.dev.qlda.repo.UserRepo;
import com.dev.qlda.response.WrapResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/dev/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3010")
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
