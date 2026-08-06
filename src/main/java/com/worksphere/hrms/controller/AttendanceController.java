package com.worksphere.hrms.controller;

import com.worksphere.hrms.dto.request.AttendanceRequest;
import com.worksphere.hrms.dto.response.ApiResponse;
import com.worksphere.hrms.dto.response.AttendanceResponse;
import com.worksphere.hrms.service.AttendanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping("/check-in")
    public ApiResponse<AttendanceResponse> checkIn(
            @Valid @RequestBody AttendanceRequest request) {

        AttendanceResponse response =
                attendanceService.checkIn(request);

        return new ApiResponse<>(
                true,
                "Check-in successful",
                LocalDateTime.now(),
                response
        );
    }

    @PostMapping("/check-out/{employeeId}")
    public ApiResponse<AttendanceResponse> checkOut(
            @PathVariable Long employeeId) {

        AttendanceResponse response =
                attendanceService.checkOut(employeeId);

        return new ApiResponse<>(
                true,
                "Check-out successful",
                LocalDateTime.now(),
                response
        );
    }

    @GetMapping("/{employeeId}")
    public ApiResponse<List<AttendanceResponse>> getAttendanceHistory(
            @PathVariable Long employeeId) {

        List<AttendanceResponse> response =
                attendanceService.getAttendanceHistory(employeeId);

        return new ApiResponse<>(
                true,
                "Attendance history fetched successfully",
                LocalDateTime.now(),
                response
        );
    }

    @GetMapping("/today")
    public ApiResponse<List<AttendanceResponse>>
    getTodayAttendance() {

        List<AttendanceResponse> response =
                attendanceService.getTodayAttendance();

        return new ApiResponse<>(
                true,
                "Today's attendance fetched successfully",
                LocalDateTime.now(),
                response
        );
    }
}