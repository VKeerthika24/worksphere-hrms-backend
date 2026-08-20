package com.worksphere.hrms.service.impl;

import com.worksphere.hrms.dto.response.DashboardResponse;
import com.worksphere.hrms.dto.response.EmployeeDashboardResponse;
import com.worksphere.hrms.entity.Employee;
import com.worksphere.hrms.entity.User;
import com.worksphere.hrms.enums.LeaveStatus;
import com.worksphere.hrms.exception.ResourceNotFoundException;
import com.worksphere.hrms.repository.AttendanceRepository;
import com.worksphere.hrms.repository.DepartmentRepository;
import com.worksphere.hrms.repository.EmployeeRepository;
import com.worksphere.hrms.repository.LeaveRepository;
import com.worksphere.hrms.repository.UserRepository;
import com.worksphere.hrms.service.DashboardService;
import com.worksphere.hrms.util.LogMessages;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

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

    private final UserRepository userRepository;


    @Override
    public Object getDashboard(String email) {

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        String role = user.getRole()
                .getName()
                .name();

        if ("EMPLOYEE".equals(role)) {
            return getEmployeeDashboard(email);
        }

        return getManagementDashboard();
    }


    // =====================================================
    // EMPLOYEE DASHBOARD
    // =====================================================

    private EmployeeDashboardResponse getEmployeeDashboard(
            String email) {

        LocalDate today =
                LocalDate.now();


        Employee employee =
                employeeRepository
                        .findByUserEmail(email)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Employee profile not found"
                                ));


        // =========================
        // TODAY'S ATTENDANCE
        // =========================

        var todayAttendance =
                attendanceRepository
                        .findByEmployeeIdAndAttendanceDate(
                                employee.getId(),
                                today
                        );


        boolean checkedInToday =
                todayAttendance.isPresent();


        boolean checkedOutToday =
                todayAttendance
                        .map(attendance ->
                                attendance.getCheckOut() != null
                        )
                        .orElse(false);


        Double todayWorkingHours =
                todayAttendance
                        .map(attendance ->
                                attendance.getWorkingHours()
                        )
                        .orElse(0.0);


        // =========================
        // LEAVE STATISTICS
        // =========================

        long totalLeaveRequests =
                leaveRepository
                        .findByEmployeeId(
                                employee.getId()
                        )
                        .size();


        long pendingLeaves =
                leaveRepository
                        .findByEmployeeId(
                                employee.getId()
                        )
                        .stream()
                        .filter(leave ->
                                leave.getStatus()
                                        == LeaveStatus.PENDING
                        )
                        .count();


        long approvedLeaves =
                leaveRepository
                        .findByEmployeeId(
                                employee.getId()
                        )
                        .stream()
                        .filter(leave ->
                                leave.getStatus()
                                        == LeaveStatus.APPROVED
                        )
                        .count();


        long rejectedLeaves =
                leaveRepository
                        .findByEmployeeId(
                                employee.getId()
                        )
                        .stream()
                        .filter(leave ->
                                leave.getStatus()
                                        == LeaveStatus.REJECTED
                        )
                        .count();


        EmployeeDashboardResponse dashboard =
                EmployeeDashboardResponse.builder()

                        .employeeId(
                                employee.getId()
                        )

                        .employeeCode(
                                employee.getEmployeeCode()
                        )

                        .employeeName(
                                employee.getFirstName()
                                        + " "
                                        + employee.getLastName()
                        )

                        .totalLeaveRequests(
                                totalLeaveRequests
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

                        .todayWorkingHours(
                                todayWorkingHours
                        )

                        .checkedInToday(
                                checkedInToday
                        )

                        .checkedOutToday(
                                checkedOutToday
                        )

                        .build();


        logger.info(
                "Employee dashboard fetched : {}",
                employee.getEmployeeCode()
        );


        return dashboard;
    }


    // =====================================================
    // ADMIN / MANAGER DASHBOARD
    // =====================================================

    private DashboardResponse getManagementDashboard() {

        LocalDate today =
                LocalDate.now();


        // =========================
        // DASHBOARD STATISTICS
        // =========================

        long totalEmployees =
                employeeRepository.count();


        long totalDepartments =
                departmentRepository.count();


        long presentToday =
                attendanceRepository
                        .countByAttendanceDate(
                                today
                        );


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
                        .calculateAverageWorkingHours(
                                today
                        );


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