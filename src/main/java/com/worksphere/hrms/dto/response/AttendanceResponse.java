package com.worksphere.hrms.dto.response;

import com.worksphere.hrms.enums.AttendanceStatus;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttendanceResponse {

    private Long id;

    private Long employeeId;

    private LocalDate attendanceDate;

    private LocalTime checkIn;

    private LocalTime checkOut;

    private Double workingHours;

    private AttendanceStatus status;

    private String employeeCode;

    private String employeeName;

    private Boolean late;

    private Double overtimeHours;
}