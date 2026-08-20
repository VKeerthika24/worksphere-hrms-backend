package com.worksphere.hrms.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDashboardResponse {

    private Long employeeId;

    private String employeeCode;

    private String employeeName;

    private Long totalLeaveRequests;

    private Long pendingLeaves;

    private Long approvedLeaves;

    private Long rejectedLeaves;

    private Double todayWorkingHours;

    private Boolean checkedInToday;

    private Boolean checkedOutToday;
}