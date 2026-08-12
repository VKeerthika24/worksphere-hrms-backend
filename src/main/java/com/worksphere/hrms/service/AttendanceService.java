package com.worksphere.hrms.service;

import com.worksphere.hrms.dto.request.AttendanceRequest;
import com.worksphere.hrms.dto.response.AttendanceResponse;

import java.util.List;

public interface AttendanceService {

    AttendanceResponse checkIn(
            AttendanceRequest request
    );

    AttendanceResponse checkOut(
            Long employeeId
    );

    List<AttendanceResponse> getAttendanceHistory(
            Long employeeId
    );

    List<AttendanceResponse> getTodayAttendance();
}