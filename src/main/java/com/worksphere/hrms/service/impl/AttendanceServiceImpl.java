package com.worksphere.hrms.service.impl;

import com.worksphere.hrms.dto.request.AttendanceRequest;
import com.worksphere.hrms.dto.response.AttendanceResponse;
import com.worksphere.hrms.entity.Attendance;
import com.worksphere.hrms.entity.Employee;
import com.worksphere.hrms.enums.AttendanceStatus;
import com.worksphere.hrms.exception.ResourceNotFoundException;
import com.worksphere.hrms.mapper.AttendanceMapper;
import com.worksphere.hrms.repository.AttendanceRepository;
import com.worksphere.hrms.repository.EmployeeRepository;
import com.worksphere.hrms.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public AttendanceResponse checkIn(AttendanceRequest request) {

        Employee employee = employeeRepository
                .findById(request.getEmployeeId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee not found"));

        attendanceRepository
                .findByEmployeeIdAndAttendanceDate(
                        employee.getId(),
                        LocalDate.now())
                .ifPresent(attendance -> {
                    throw new IllegalArgumentException(
                            "Employee has already checked in today");
                });

        Attendance attendance = Attendance.builder()
                .attendanceDate(LocalDate.now())
                .checkIn(LocalTime.now())
                .workingHours(0.0)
                .status(AttendanceStatus.PRESENT)
                .employee(employee)
                .build();

        Attendance savedAttendance = attendanceRepository.save(attendance);

        return AttendanceMapper.toResponse(savedAttendance);
    }

    @Override
    public List<AttendanceResponse> getAttendanceHistory(Long employeeId) {

        return attendanceRepository
                .findByEmployeeId(employeeId)
                .stream()
                .map(AttendanceMapper::toResponse)
                .toList();
    }

    @Override
    public AttendanceResponse checkOut(Long employeeId) {

        Attendance attendance = attendanceRepository
                .findByEmployeeIdAndAttendanceDate(
                        employeeId,
                        LocalDate.now())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee has not checked in today"));

        if (attendance.getCheckOut() != null) {
            throw new IllegalArgumentException(
                    "Employee has already checked out today");
        }

        LocalTime checkOutTime = LocalTime.now();

        attendance.setCheckOut(checkOutTime);

        long minutes = java.time.Duration
                .between(attendance.getCheckIn(), checkOutTime)
                .toMinutes();

        double workingHours =
                Math.round((minutes / 60.0) * 100.0) / 100.0;

        attendance.setWorkingHours(workingHours);

        Attendance updatedAttendance =
                attendanceRepository.save(attendance);

        return AttendanceMapper.toResponse(updatedAttendance);
    }
}