package com.worksphere.hrms.mapper;

import com.worksphere.hrms.dto.response.AttendanceResponse;
import com.worksphere.hrms.entity.Attendance;

public class AttendanceMapper {

    private AttendanceMapper() {
    }

    public static AttendanceResponse toResponse(Attendance attendance) {

        return AttendanceResponse.builder()
                .id(attendance.getId())
                .attendanceDate(attendance.getAttendanceDate())
                .checkIn(attendance.getCheckIn())
                .checkOut(attendance.getCheckOut())
                .workingHours(attendance.getWorkingHours())
                .late(attendance.getLate())
                .overtimeHours(attendance.getOvertimeHours())
                .status(attendance.getStatus())
                .employeeCode(attendance.getEmployee().getEmployeeCode())
                .employeeName(
                        attendance.getEmployee().getFirstName()
                                + " "
                                + attendance.getEmployee().getLastName()
                )
                .build();
    }
}