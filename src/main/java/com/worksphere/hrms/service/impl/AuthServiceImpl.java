package com.worksphere.hrms.service.impl;

import com.worksphere.hrms.dto.request.LoginRequest;
import com.worksphere.hrms.dto.request.RegisterRequest;
import com.worksphere.hrms.dto.response.LoginResponse;
import com.worksphere.hrms.dto.response.RegisterResponse;
import com.worksphere.hrms.entity.Role;
import com.worksphere.hrms.entity.User;
import com.worksphere.hrms.enums.RoleType;
import com.worksphere.hrms.exception.DuplicateResourceException;
import com.worksphere.hrms.exception.ResourceNotFoundException;
import com.worksphere.hrms.mapper.AuthMapper;
import com.worksphere.hrms.repository.RoleRepository;
import com.worksphere.hrms.repository.UserRepository;
import com.worksphere.hrms.security.JwtService;
import com.worksphere.hrms.service.AuthService;
import com.worksphere.hrms.util.LogMessages;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static final Logger logger =
            LoggerFactory.getLogger(AuthServiceImpl.class);

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public LoginResponse login(LoginRequest request) {

        try {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

        } catch (BadCredentialsException ex) {

            logger.warn(
                    "Invalid login attempt : {}",
                    request.getEmail()
            );

            throw ex;
        }

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(user.getRole().getName().name())
                .build();

        String token = jwtService.generateToken(userDetails);

        logger.info(
                "{} : {}",
                LogMessages.LOGIN_SUCCESS,
                user.getEmail()
        );

        return LoginResponse.builder()
                .token(token)
                .email(user.getEmail())
                .role(user.getRole().getName().name())
                .build();
    }

    @Override
    public RegisterResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {

            logger.warn(
                    "Registration failed. Email already exists : {}",
                    request.getEmail()
            );

            throw new DuplicateResourceException("Email already exists");
        }

        Role role = roleRepository.findByName(RoleType.EMPLOYEE)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Role not found"));

        String encodedPassword =
                passwordEncoder.encode(request.getPassword());

        User user =
                AuthMapper.toEntity(request, role, encodedPassword);

        User savedUser =
                userRepository.save(user);

        logger.info(
                "{} : {}",
                LogMessages.REGISTER_SUCCESS,
                savedUser.getEmail()
        );

        return AuthMapper.toResponse(savedUser);
    }
}