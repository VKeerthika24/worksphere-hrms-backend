package com.worksphere.hrms.controller;

import com.worksphere.hrms.dto.request.LeaveRequest;
import com.worksphere.hrms.dto.response.ApiResponse;
import com.worksphere.hrms.dto.response.LeaveResponse;
import com.worksphere.hrms.service.LeaveService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/leaves")
@RequiredArgsConstructor
public class LeaveController {

    private final LeaveService leaveService;

    @PostMapping
    public ApiResponse<LeaveResponse> applyLeave(
            @Valid @RequestBody LeaveRequest request) {

        LeaveResponse response =
                leaveService.applyLeave(request);

        return new ApiResponse<>(
                true,
                "Leave applied successfully",
                LocalDateTime.now(),
                response
        );
    }

    @GetMapping("/{employeeId}")
    public ApiResponse<List<LeaveResponse>> getEmployeeLeaves(
            @PathVariable Long employeeId) {

        List<LeaveResponse> response =
                leaveService.getEmployeeLeaves(employeeId);

        return new ApiResponse<>(
                true,
                "Leave history fetched successfully",
                LocalDateTime.now(),
                response
        );
    }

    @PutMapping("/{leaveId}/approve")
    public ApiResponse<LeaveResponse> approveLeave(
            @PathVariable Long leaveId) {

        LeaveResponse response =
                leaveService.approveLeave(leaveId);

        return new ApiResponse<>(
                true,
                "Leave approved successfully",
                LocalDateTime.now(),
                response
        );
    }

    @PutMapping("/{leaveId}/reject")
    public ApiResponse<LeaveResponse> rejectLeave(
            @PathVariable Long leaveId) {

        LeaveResponse response =
                leaveService.rejectLeave(leaveId);

        return new ApiResponse<>(
                true,
                "Leave rejected successfully",
                LocalDateTime.now(),
                response
        );
    }

    @GetMapping
    public ApiResponse<List<LeaveResponse>> getAllLeaves() {

        List<LeaveResponse> response =
                leaveService.getAllLeaves();

        return new ApiResponse<>(
                true,
                "Leave requests fetched successfully",
                LocalDateTime.now(),
                response
        );
    }
}