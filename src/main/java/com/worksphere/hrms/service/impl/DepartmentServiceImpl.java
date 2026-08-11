package com.worksphere.hrms.service.impl;

import com.worksphere.hrms.entity.Department;
import com.worksphere.hrms.exception.ResourceNotFoundException;
import com.worksphere.hrms.repository.DepartmentRepository;
import com.worksphere.hrms.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    // =========================
    // CREATE DEPARTMENT
    // =========================

    @Override
    public Department createDepartment(
            Department department) {

        if (departmentRepository.existsByName(
                department.getName())) {

            throw new IllegalArgumentException(
                    "Department already exists"
            );
        }

        return departmentRepository.save(
                department
        );
    }


    // =========================
    // GET ALL DEPARTMENTS
    // =========================

    @Override
    public List<Department> getAllDepartments() {

        return departmentRepository.findAll();
    }


    // =========================
    // GET DEPARTMENT BY ID
    // =========================

    @Override
    public Department getDepartmentById(
            Long id) {

        return departmentRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Department not found"
                        )
                );
    }


    // =========================
    // UPDATE DEPARTMENT
    // =========================

    @Override
    public Department updateDepartment(
            Long id,
            Department department) {

        Department existingDepartment =
                departmentRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Department not found"
                                )
                        );

        existingDepartment.setName(
                department.getName()
        );

        existingDepartment.setDescription(
                department.getDescription()
        );

        return departmentRepository.save(
                existingDepartment
        );
    }


    // =========================
    // DELETE DEPARTMENT
    // =========================

    @Override
    public void deleteDepartment(
            Long id) {

        Department department =
                departmentRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Department not found"
                                )
                        );

        departmentRepository.delete(
                department
        );
    }
}