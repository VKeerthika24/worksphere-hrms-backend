package com.worksphere.hrms.service.impl;

import com.worksphere.hrms.dto.request.LoginRequest;
import com.worksphere.hrms.dto.request.RegisterRequest;
import com.worksphere.hrms.dto.response.LoginResponse;
import com.worksphere.hrms.dto.response.RegisterResponse;
import com.worksphere.hrms.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.worksphere.hrms.entity.Role;
import com.worksphere.hrms.entity.User;
import com.worksphere.hrms.enums.RoleType;
import com.worksphere.hrms.exception.DuplicateResourceException;
import com.worksphere.hrms.exception.ResourceNotFoundException;
import com.worksphere.hrms.repository.RoleRepository;
import com.worksphere.hrms.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.worksphere.hrms.mapper.AuthMapper;
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public LoginResponse login(LoginRequest request) {
        throw new UnsupportedOperationException("Login will be implemented after JWT configuration.");
    }

    @Override
    public RegisterResponse register(RegisterRequest request) {

        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }

        // Get EMPLOYEE role
        Role role = roleRepository.findByName(RoleType.EMPLOYEE)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Role not found"));

        // Encrypt password
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // Convert DTO to Entity
        User user = AuthMapper.toEntity(request, role, encodedPassword);

        // Save User
        User savedUser = userRepository.save(user);

        // Convert Entity to Response
        return AuthMapper.toResponse(savedUser);
    }
}