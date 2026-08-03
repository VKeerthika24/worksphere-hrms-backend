package com.worksphere.hrms.controller;

import com.worksphere.hrms.dto.request.RegisterRequest;
import com.worksphere.hrms.dto.response.ApiResponse;
import com.worksphere.hrms.dto.response.RegisterResponse;
import com.worksphere.hrms.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import com.worksphere.hrms.dto.request.LoginRequest;
import com.worksphere.hrms.dto.response.LoginResponse;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ApiResponse<RegisterResponse> register(
            @Valid @RequestBody RegisterRequest request) {

        RegisterResponse response = authService.register(request);

        return new ApiResponse<>(
                true,
                "User registered successfully",
                LocalDateTime.now(),
                response
        );
    }

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(
            @Valid @RequestBody LoginRequest request) {

        LoginResponse response = authService.login(request);

        return new ApiResponse<>(
                true,
                "Login successful",
                LocalDateTime.now(),
                response
        );
    }
}