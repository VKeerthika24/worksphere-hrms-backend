package com.worksphere.hrms.service.impl;

import com.worksphere.hrms.dto.response.DashboardResponse;
import com.worksphere.hrms.enums.LeaveStatus;
import com.worksphere.hrms.repository.AttendanceRepository;
import com.worksphere.hrms.repository.DepartmentRepository;
import com.worksphere.hrms.repository.EmployeeRepository;
import com.worksphere.hrms.repository.LeaveRepository;
import com.worksphere.hrms.service.DashboardService;
import com.worksphere.hrms.util.LogMessages;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl
        implements DashboardService {

    private static final Logger logger =
            LoggerFactory.getLogger(
                    DashboardServiceImpl.class
            );

    private final EmployeeRepository employeeRepository;

    private final DepartmentRepository departmentRepository;

    private final AttendanceRepository attendanceRepository;

    private final LeaveRepository leaveRepository;


    @Override
    public DashboardResponse getDashboard() {

        LocalDate today = LocalDate.now();


        // =========================
        // DASHBOARD STATISTICS
        // =========================

        long totalEmployees =
                employeeRepository.count();

        long totalDepartments =
                departmentRepository.count();

        long presentToday =
                attendanceRepository
                        .countByAttendanceDate(today);

        long lateToday =
                attendanceRepository
                        .countByAttendanceDateAndLate(
                                today,
                                true
                        );

        long employeesOnLeave =
                leaveRepository
                        .countByStatusAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                                LeaveStatus.APPROVED,
                                today,
                                today
                        );

        long pendingLeaves =
                leaveRepository
                        .countByStatus(
                                LeaveStatus.PENDING
                        );

        long approvedLeaves =
                leaveRepository
                        .countByStatus(
                                LeaveStatus.APPROVED
                        );

        long rejectedLeaves =
                leaveRepository
                        .countByStatus(
                                LeaveStatus.REJECTED
                        );


        // =========================
        // AVERAGE WORKING HOURS
        // =========================

        Double averageWorkingHours =
                attendanceRepository
                        .calculateAverageWorkingHours(today);


        // =========================
        // BUILD RESPONSE
        // =========================

        DashboardResponse dashboard =
                DashboardResponse.builder()

                        .totalEmployees(
                                totalEmployees
                        )

                        .totalDepartments(
                                totalDepartments
                        )

                        .presentToday(
                                presentToday
                        )

                        .lateToday(
                                lateToday
                        )

                        .employeesOnLeave(
                                employeesOnLeave
                        )

                        .pendingLeaves(
                                pendingLeaves
                        )

                        .approvedLeaves(
                                approvedLeaves
                        )

                        .rejectedLeaves(
                                rejectedLeaves
                        )

                        .averageWorkingHours(
                                averageWorkingHours
                        )

                        .build();


        logger.info(
                LogMessages.DASHBOARD_FETCHED
        );


        return dashboard;
    }
}