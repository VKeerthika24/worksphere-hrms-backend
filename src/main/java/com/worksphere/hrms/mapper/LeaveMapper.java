package com.worksphere.hrms.mapper;

import com.worksphere.hrms.dto.response.LeaveResponse;
import com.worksphere.hrms.entity.Employee;
import com.worksphere.hrms.entity.Leave;
import com.worksphere.hrms.enums.LeaveStatus;

import com.worksphere.hrms.dto.request.LeaveRequest;


public class LeaveMapper {

    private LeaveMapper() {
    }

    public static Leave toEntity(LeaveRequest request, Employee employee) {

        return Leave.builder()
                .leaveType(request.getLeaveType())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .reason(request.getReason())
                .status(LeaveStatus.PENDING)
                .employee(employee)
                .build();
    }

    public static LeaveResponse toResponse(Leave leave) {

        return LeaveResponse.builder()
                .id(leave.getId())
                .employeeCode(leave.getEmployee().getEmployeeCode())
                .employeeName(
                        leave.getEmployee().getFirstName() + " "
                                + leave.getEmployee().getLastName())
                .leaveType(leave.getLeaveType())
                .startDate(leave.getStartDate())
                .endDate(leave.getEndDate())
                .reason(leave.getReason())
                .status(leave.getStatus())
                .build();
    }
}