package com.worksphere.hrms.security;

import com.worksphere.hrms.entity.Employee;
import com.worksphere.hrms.exception.ResourceNotFoundException;
import com.worksphere.hrms.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUserService {

    private final EmployeeRepository employeeRepository;

    /**
     * Returns the email of the currently authenticated user.
     */
    public String getCurrentUserEmail() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated()) {

            throw new IllegalStateException(
                    "User is not authenticated"
            );
        }

        return authentication.getName();
    }


    /**
     * Returns the Employee associated
     * with the currently authenticated user.
     */
    public Employee getCurrentEmployee() {

        String email = getCurrentUserEmail();

        return employeeRepository
                .findByUserEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee profile not found"
                        )
                );
    }


    /**
     * Returns the Employee ID of
     * the currently authenticated user.
     */
    public Long getCurrentEmployeeId() {

        return getCurrentEmployee().getId();
    }
}