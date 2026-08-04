package com.worksphere.hrms.service;

import com.worksphere.hrms.dto.request.EmployeeRequest;
import com.worksphere.hrms.dto.response.EmployeeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface EmployeeService {

    EmployeeResponse createEmployee(EmployeeRequest request);

    List<EmployeeResponse> getAllEmployees();

    EmployeeResponse getEmployeeById(Long id);

    EmployeeResponse updateEmployee(Long id, EmployeeRequest request);

    void deleteEmployee(Long id);

    List<EmployeeResponse> searchEmployees(String firstName);

    Page<EmployeeResponse> getEmployees(Pageable pageable);
}