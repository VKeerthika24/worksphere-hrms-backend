package com.worksphere.hrms.controller;

import com.worksphere.hrms.dto.response.ApiResponse;
import com.worksphere.hrms.dto.response.UserResponse;
import com.worksphere.hrms.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ApiResponse<List<UserResponse>> getAllUsers() {

        List<UserResponse> users =
                userService.getAllUsers();

        return new ApiResponse<>(
                true,
                "Users fetched successfully",
                LocalDateTime.now(),
                users
        );
    }
}