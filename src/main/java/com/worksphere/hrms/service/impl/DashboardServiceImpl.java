package com.worksphere.hrms.service.impl;

import com.worksphere.hrms.dto.response.DashboardResponse;
import com.worksphere.hrms.enums.LeaveStatus;
import com.worksphere.hrms.repository.AttendanceRepository;
import com.worksphere.hrms.repository.DepartmentRepository;
import com.worksphere.hrms.repository.EmployeeRepository;
import com.worksphere.hrms.repository.LeaveRepository;
import com.worksphere.hrms.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final AttendanceRepository attendanceRepository;
    private final LeaveRepository leaveRepository;

    @Override
    public DashboardResponse getDashboard() {

        LocalDate today = LocalDate.now();

        return DashboardResponse.builder()
                .totalEmployees(employeeRepository.count())
                .totalDepartments(departmentRepository.count())
                .presentToday(
                        attendanceRepository.countByAttendanceDate(today)
                )
                .lateToday(
                attendanceRepository.countByAttendanceDateAndLate(
                        today,
                        true
                )
        )
                .employeesOnLeave(
                        leaveRepository
                                .countByStatusAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                                        LeaveStatus.APPROVED,
                                        today,
                                        today
                                )
                )
                .pendingLeaves(
                        leaveRepository.countByStatus(LeaveStatus.PENDING)
                )
                .approvedLeaves(
                        leaveRepository.countByStatus(LeaveStatus.APPROVED)
                )
                .rejectedLeaves(
                        leaveRepository.countByStatus(LeaveStatus.REJECTED)
                )
                .build();
    }
}