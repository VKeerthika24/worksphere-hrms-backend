package com.worksphere.hrms.controller;

import com.worksphere.hrms.dto.response.ApiResponse;
import com.worksphere.hrms.entity.Department;
import com.worksphere.hrms.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping
    public ApiResponse<List<Department>> getAllDepartments() {

        List<Department> departments =
                departmentService.getAllDepartments();

        return new ApiResponse<>(
                true,
                "Departments fetched successfully",
                LocalDateTime.now(),
                departments
        );
    }
}