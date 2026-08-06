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

import java.time.Duration;
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

        LocalTime currentTime = LocalTime.now();
        LocalTime officeStart = LocalTime.of(9, 30);

        boolean late = currentTime.isAfter(officeStart);

        Attendance attendance = Attendance.builder()
                .attendanceDate(LocalDate.now())
                .checkIn(currentTime)
                .workingHours(0.0)
                .late(late)
                .overtimeHours(0.0)
                .status(AttendanceStatus.PRESENT)
                .employee(employee)
                .build();

        Attendance savedAttendance = attendanceRepository.save(attendance);

        return AttendanceMapper.toResponse(savedAttendance);
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

        long minutes = Duration
                .between(attendance.getCheckIn(), checkOutTime)
                .toMinutes();

        double workingHours = minutes / 60.0;

        workingHours = Math.round(workingHours * 100.0) / 100.0;

        attendance.setWorkingHours(workingHours);

        // Half Day Logic
        if (workingHours < 4) {
            attendance.setStatus(AttendanceStatus.HALF_DAY);
        } else {
            attendance.setStatus(AttendanceStatus.PRESENT);
        }

        // Overtime Logic
        double overtime = 0.0;

        if (workingHours > 8) {
            overtime = workingHours - 8;
        }

        attendance.setOvertimeHours(
                Math.round(overtime * 100.0) / 100.0);

        Attendance updatedAttendance =
                attendanceRepository.save(attendance);

        return AttendanceMapper.toResponse(updatedAttendance);
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
    public List<AttendanceResponse> getTodayAttendance() {

        return attendanceRepository
                .findByAttendanceDate(LocalDate.now())
                .stream()
                .map(AttendanceMapper::toResponse)
                .toList();
    }
}