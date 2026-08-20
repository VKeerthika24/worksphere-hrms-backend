package com.worksphere.hrms.controller;

import com.worksphere.hrms.dto.response.ApiResponse;
import com.worksphere.hrms.dto.response.DashboardResponse;
import com.worksphere.hrms.dto.response.EmployeeDashboardResponse;
import com.worksphere.hrms.service.DashboardService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Tag(
        name = "Dashboard",
        description = "Dashboard Statistics APIs"
)
@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping
    public ApiResponse<?> getDashboard(
            Authentication authentication) {

        Object response =
                dashboardService.getDashboard(
                        authentication.getName()
                );

        return new ApiResponse<>(
                true,
                "Dashboard fetched successfully",
                LocalDateTime.now(),
                response
        );
    }
}