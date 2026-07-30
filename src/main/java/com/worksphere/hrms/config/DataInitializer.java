package com.worksphere.hrms.config;

import com.worksphere.hrms.entity.Role;
import com.worksphere.hrms.enums.RoleType;
import com.worksphere.hrms.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) {

        createRoleIfNotExists(RoleType.ADMIN, "System Administrator");
        createRoleIfNotExists(RoleType.MANAGER, "Department Manager");
        createRoleIfNotExists(RoleType.EMPLOYEE, "Company Employee");
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
}