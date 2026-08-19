package com.worksphere.hrms.service.impl;

import com.worksphere.hrms.dto.request.EmployeeRequest;
import com.worksphere.hrms.dto.response.EmployeeResponse;
import com.worksphere.hrms.entity.Department;
import com.worksphere.hrms.entity.Employee;
import com.worksphere.hrms.entity.User;
import com.worksphere.hrms.exception.ResourceNotFoundException;
import com.worksphere.hrms.mapper.EmployeeMapper;
import com.worksphere.hrms.repository.DepartmentRepository;
import com.worksphere.hrms.repository.EmployeeRepository;
import com.worksphere.hrms.repository.UserRepository;
import com.worksphere.hrms.service.EmployeeService;
import com.worksphere.hrms.util.LogMessages;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.worksphere.hrms.enums.EmployeeStatus;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger logger =
            LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;

    @Override
    public EmployeeResponse createEmployee(EmployeeRequest request) {

        Department department = departmentRepository
                .findById(request.getDepartmentId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Department not found"));

        User user = userRepository
                .findById(request.getUserId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Employee employee =
                EmployeeMapper.toEntity(request, user, department);

        Employee savedEmployee =
                employeeRepository.save(employee);

        logger.info(
                "{} : {}",
                LogMessages.EMPLOYEE_CREATED,
                savedEmployee.getEmployeeCode()
        );

        return EmployeeMapper.toResponse(savedEmployee);
    }

    @Override
    public List<EmployeeResponse> getAllEmployees() {

        return employeeRepository.findAll()
                .stream()
                .map(EmployeeMapper::toResponse)
                .toList();
    }

    @Override
    public EmployeeResponse getEmployeeById(Long id) {

        Employee employee = employeeRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee not found"));

        return EmployeeMapper.toResponse(employee);
    }

    @Override
    public EmployeeResponse updateEmployee(
            Long id,
            EmployeeRequest request) {

        Employee employee = employeeRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee not found"));

        Department department = departmentRepository
                .findById(request.getDepartmentId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Department not found"));

        User user = userRepository
                .findById(request.getUserId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setGender(request.getGender());
        employee.setDateOfBirth(request.getDateOfBirth());
        employee.setPhoneNumber(request.getPhoneNumber());
        employee.setAddress(request.getAddress());
        employee.setDesignation(request.getDesignation());
        employee.setSalary(request.getSalary());
        employee.setJoiningDate(request.getJoiningDate());
        employee.setDepartment(department);
        employee.setUser(user);

        Employee updatedEmployee =
                employeeRepository.save(employee);

        logger.info(
                "{} : {}",
                LogMessages.EMPLOYEE_UPDATED,
                updatedEmployee.getEmployeeCode()
        );

        return EmployeeMapper.toResponse(updatedEmployee);
    }

    @Override
    public void deleteEmployee(Long id) {

        Employee employee = employeeRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee not found"));

        employee.setStatus(EmployeeStatus.INACTIVE);

        employeeRepository.save(employee);

        logger.info(
                "{} : {}",
                LogMessages.EMPLOYEE_DELETED,
                employee.getEmployeeCode()
        );
    }

    @Override
    public List<EmployeeResponse> searchEmployees(String firstName) {

        return employeeRepository
                .findByFirstNameContainingIgnoreCase(firstName)
                .stream()
                .map(EmployeeMapper::toResponse)
                .toList();
    }

    @Override
    public Page<EmployeeResponse> getEmployees(Pageable pageable) {

        return employeeRepository
                .findAll(pageable)
                .map(EmployeeMapper::toResponse);
    }

    @Override
    public EmployeeResponse getCurrentEmployee(String email) {

        Employee employee = employeeRepository
                .findByUserEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee profile not found"
                        ));

        return EmployeeMapper.toResponse(employee);
    }
}