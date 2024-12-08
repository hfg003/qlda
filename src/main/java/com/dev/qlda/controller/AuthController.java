package com.dev.qlda.controller;

import com.dev.qlda.entity.Users;
import com.dev.qlda.repo.UserRepo;
import com.dev.qlda.request.SignInRequest;
import com.dev.qlda.response.LoginResponse;
import com.dev.qlda.response.WrapResponse;
import com.dev.qlda.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepo userRepo;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    public WrapResponse<?> signIn(@RequestBody SignInRequest signInRequest) {
        if (StringUtils.isBlank(signInRequest.getUsername()) || StringUtils.isBlank(signInRequest.getPassword())) {
            return WrapResponse.error("thong tin tai khoan hoac mat khau khong chinh xac");
        }
        Users user = userRepo.findByUsername(signInRequest.getUsername()).orElse(null);
        if (user == null) {
            return WrapResponse.error("user not found");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword()));

        return WrapResponse.ok("dang nhap thanh cong", LoginResponse.builder()
                .id(user.getId())
                .message("dang nhap thanh cong")
                .token(jwtUtils.generateToken(user))
                .refreshToken(jwtUtils.generateRefreshToken(new HashMap<>(), user))
                .username(user.getUsername())
                .build());
    }
}
