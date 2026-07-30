package com.worksphere.hrms.service.impl;

import com.worksphere.hrms.dto.response.HealthResponse;
import com.worksphere.hrms.service.HealthService;
import org.springframework.stereotype.Service;

@Service
public class HealthServiceImpl implements HealthService {

    @Override
    public HealthResponse healthCheck() {

        return new HealthResponse(
                "UP",
                "WorkSphere HRMS",
                "1.0.0"
        );

    }
}