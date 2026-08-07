package com.worksphere.hrms.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardResponse {

    private Long totalEmployees;

    private Long totalDepartments;

    private Long presentToday;

    private Long lateToday;

    private Long employeesOnLeave;

    private Long pendingLeaves;

    private Long approvedLeaves;

    private Long rejectedLeaves;

    private Double averageWorkingHours;
}