package com.worksphere.hrms.service;

import com.worksphere.hrms.dto.request.AttendanceRequest;
import com.worksphere.hrms.dto.response.AttendanceResponse;
import com.worksphere.hrms.service.impl.AttendanceServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

public interface AttendanceService {

    AttendanceResponse checkIn(AttendanceRequest request);

    AttendanceResponse checkOut(Long employeeId);

    List<AttendanceResponse> getAttendanceHistory(Long employeeId);

    List<AttendanceResponse> getTodayAttendance();

    static final Logger logger =
            LoggerFactory.getLogger(
                    AttendanceServiceImpl.class);
}