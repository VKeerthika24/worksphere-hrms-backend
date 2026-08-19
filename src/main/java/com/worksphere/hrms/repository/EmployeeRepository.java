package com.worksphere.hrms.repository;

import com.worksphere.hrms.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository
        extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmployeeCode(
            String employeeCode
    );

    boolean existsByEmployeeCode(
            String employeeCode
    );

    List<Employee> findByFirstNameContainingIgnoreCase(
            String firstName
    );

    Optional<Employee> findByUserEmail(
            String email
    );


}