package com.worksphere.hrms.controller;

import com.worksphere.hrms.dto.request.EmployeeRequest;
import com.worksphere.hrms.dto.response.ApiResponse;
import com.worksphere.hrms.dto.response.EmployeeResponse;
import com.worksphere.hrms.service.EmployeeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;
@Tag(
        name = "Employee Management",
        description = "Employee CRUD APIs"
)
@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<EmployeeResponse> createEmployee(
            @Valid @RequestBody EmployeeRequest request) {

        EmployeeResponse response = employeeService.createEmployee(request);

        return new ApiResponse<>(
                true,
                "Employee created successfully",
                LocalDateTime.now(),
                response
        );
    }

    @GetMapping
    public ApiResponse<List<EmployeeResponse>> getAllEmployees() {

        List<EmployeeResponse> employees = employeeService.getAllEmployees();

        return new ApiResponse<>(
                true,
                "Employees fetched successfully",
                java.time.LocalDateTime.now(),
                employees
        );
    }

    @PutMapping("/{id}")
    public ApiResponse<EmployeeResponse> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeRequest request) {

        EmployeeResponse response =
                employeeService.updateEmployee(id, request);

        return new ApiResponse<>(
                true,
                "Employee updated successfully",
                java.time.LocalDateTime.now(),
                response
        );
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteEmployee(@PathVariable Long id) {

        employeeService.deleteEmployee(id);

        return new ApiResponse<>(
                true,
                "Employee deleted successfully",
                java.time.LocalDateTime.now(),
                "Employee deleted successfully"
        );
    }

    @GetMapping("/search")
    public ApiResponse<List<EmployeeResponse>> searchEmployees(
            @RequestParam String firstName) {

        List<EmployeeResponse> employees =
                employeeService.searchEmployees(firstName);

        return new ApiResponse<>(
                true,
                "Employees fetched successfully",
                java.time.LocalDateTime.now(),
                employees
        );
    }

    @GetMapping("/page")
    public ApiResponse<Page<EmployeeResponse>> getEmployees(
            Pageable pageable) {

        Page<EmployeeResponse> employees =
                employeeService.getEmployees(pageable);

        return new ApiResponse<>(
                true,
                "Employees fetched successfully",
                java.time.LocalDateTime.now(),
                employees
        );
    }
}