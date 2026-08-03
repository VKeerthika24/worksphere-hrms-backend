package com.worksphere.hrms.mapper;

import com.worksphere.hrms.dto.request.RegisterRequest;
import com.worksphere.hrms.dto.response.RegisterResponse;
import com.worksphere.hrms.entity.Role;
import com.worksphere.hrms.entity.User;

public class AuthMapper {

    private AuthMapper() {
        // Prevent instantiation
    }

    /**
     * Convert RegisterRequest DTO to User Entity
     */
    public static User toEntity(RegisterRequest request,
                                Role role,
                                String encodedPassword) {

        return User.builder()
                .email(request.getEmail())
                .password(encodedPassword)
                .role(role)
                .enabled(true)
                .accountLocked(false)
                .build();
    }

    /**
     * Convert User Entity to RegisterResponse DTO
     */
    public static RegisterResponse toResponse(User user) {

        return RegisterResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .role(user.getRole().getName().name())
                .message("User registered successfully")
                .build();
    }
}