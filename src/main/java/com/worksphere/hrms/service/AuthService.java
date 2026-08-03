package com.worksphere.hrms.service;

import com.worksphere.hrms.dto.request.LoginRequest;
import com.worksphere.hrms.dto.request.RegisterRequest;
import com.worksphere.hrms.dto.response.LoginResponse;
import com.worksphere.hrms.dto.response.RegisterResponse;

public interface AuthService {

    RegisterResponse register(RegisterRequest request);

    LoginResponse login(LoginRequest request);

}