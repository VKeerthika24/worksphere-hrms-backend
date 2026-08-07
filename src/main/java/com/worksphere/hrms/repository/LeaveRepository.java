package com.worksphere.hrms.repository;

import com.worksphere.hrms.entity.Leave;
import org.springframework.data.jpa.repository.JpaRepository;
import com.worksphere.hrms.enums.LeaveStatus;
import java.time.LocalDate;
import java.util.List;

public interface LeaveRepository
        extends JpaRepository<Leave,Long> {

    List<Leave> findByEmployeeId(Long employeeId);

    long countByStatus(LeaveStatus status);

    long countByStatusAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            LeaveStatus status,
            LocalDate today1,
            LocalDate today2
    );
}