package com.worksphere.hrms.controller;

import com.worksphere.hrms.dto.response.ApiResponse;
import com.worksphere.hrms.dto.response.HealthResponse;
import com.worksphere.hrms.service.HealthService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class HealthController {

    private final HealthService healthService;

    public HealthController(HealthService healthService) {
        this.healthService = healthService;
    }

    @GetMapping("/api/health")
    public ApiResponse<HealthResponse> health() {

        return new ApiResponse<>(
                true,
                "Health check successful",
                LocalDateTime.now(),
                healthService.healthCheck()
        );

    }

}