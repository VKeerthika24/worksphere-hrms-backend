package com.worksphere.hrms.controller;

import com.worksphere.hrms.dto.response.ApiResponse;
import com.worksphere.hrms.entity.Department;
import com.worksphere.hrms.service.DepartmentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Tag(
        name = "Department Management",
        description = "Department CRUD APIs"
)
@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;


    // =========================
    // CREATE DEPARTMENT
    // =========================

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Department> createDepartment(
            @RequestBody Department department) {

        Department createdDepartment =
                departmentService.createDepartment(department);

        return new ApiResponse<>(
                true,
                "Department created successfully",
                LocalDateTime.now(),
                createdDepartment
        );
    }


    // =========================
    // GET ALL DEPARTMENTS
    // =========================

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


    // =========================
    // GET DEPARTMENT BY ID
    // =========================

    @GetMapping("/{id}")
    public ApiResponse<Department> getDepartmentById(
            @PathVariable Long id) {

        Department department =
                departmentService.getDepartmentById(id);

        return new ApiResponse<>(
                true,
                "Department fetched successfully",
                LocalDateTime.now(),
                department
        );
    }


    // =========================
    // UPDATE DEPARTMENT
    // =========================

    @PutMapping("/{id}")
    public ApiResponse<Department> updateDepartment(
            @PathVariable Long id,
            @RequestBody Department department) {

        Department updatedDepartment =
                departmentService.updateDepartment(
                        id,
                        department
                );

        return new ApiResponse<>(
                true,
                "Department updated successfully",
                LocalDateTime.now(),
                updatedDepartment
        );
    }


    // =========================
    // DELETE DEPARTMENT
    // =========================

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteDepartment(
            @PathVariable Long id) {

        departmentService.deleteDepartment(id);

        return new ApiResponse<>(
                true,
                "Department deleted successfully",
                LocalDateTime.now(),
                "Department deleted successfully"
        );
    }
}