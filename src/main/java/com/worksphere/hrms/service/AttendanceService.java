package com.worksphere.hrms.service;

import com.worksphere.hrms.dto.request.AttendanceRequest;
import com.worksphere.hrms.dto.response.AttendanceResponse;

import java.util.List;

public interface AttendanceService {

    AttendanceResponse checkIn(AttendanceRequest request);

    List<AttendanceResponse> getAttendanceHistory(Long employeeId);
}