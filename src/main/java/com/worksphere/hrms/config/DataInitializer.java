package com.worksphere.hrms.config;

import com.worksphere.hrms.entity.Department;
import com.worksphere.hrms.entity.Role;
import com.worksphere.hrms.enums.RoleType;
import com.worksphere.hrms.repository.DepartmentRepository;
import com.worksphere.hrms.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final DepartmentRepository departmentRepository;

    @Override
    public void run(String... args) {

        // Seed Roles
        createRoleIfNotExists(RoleType.ADMIN, "System Administrator");
        createRoleIfNotExists(RoleType.MANAGER, "Department Manager");
        createRoleIfNotExists(RoleType.EMPLOYEE, "Company Employee");

        // Seed Departments
        createDepartmentIfNotExists("IT", "Information Technology");
        createDepartmentIfNotExists("HR", "Human Resources");
        createDepartmentIfNotExists("Finance", "Finance Department");
        createDepartmentIfNotExists("Marketing", "Marketing Department");
        createDepartmentIfNotExists("Sales", "Sales Department");
    }

    private void createRoleIfNotExists(RoleType roleType, String description) {

        if (roleRepository.findByName(roleType).isEmpty()) {

            Role role = Role.builder()
                    .name(roleType)
                    .description(description)
                    .build();

            roleRepository.save(role);
        }
    }

    private void createDepartmentIfNotExists(String name, String description) {

        if (departmentRepository.findByName(name).isEmpty()) {

            Department department = Department.builder()
                    .name(name)
                    .description(description)
                    .build();

            departmentRepository.save(department);
        }
    }
}