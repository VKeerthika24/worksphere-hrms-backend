package com.worksphere.hrms.dto.response;

import com.worksphere.hrms.enums.EmployeeStatus;
import com.worksphere.hrms.enums.Gender;

import java.math.BigDecimal;
import java.time.LocalDate;

public class EmployeeResponse {

    private Long id;

    private String employeeCode;

    private String firstName;

    private String lastName;

    private Gender gender;

    private LocalDate dateOfBirth;

    private String phoneNumber;

    private String address;

    private String designation;

    private BigDecimal salary;

    private LocalDate joiningDate;

    private EmployeeStatus status;

    private String departmentName;

    private String email;

    // Generate Getters and Setters
}