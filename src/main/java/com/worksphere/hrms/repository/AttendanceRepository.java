package com.worksphere.hrms.repository;

import com.worksphere.hrms.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository
        extends JpaRepository<Attendance, Long> {

    Optional<Attendance> findByEmployeeIdAndAttendanceDate(
            Long employeeId,
            LocalDate attendanceDate
    );

    List<Attendance> findByEmployeeId(Long employeeId);

    List<Attendance> findByAttendanceDate(
            LocalDate attendanceDate
    );

    long countByAttendanceDate(
            LocalDate attendanceDate
    );

    long countByAttendanceDateAndLate(
            LocalDate attendanceDate,
            Boolean late
    );

    @Query("""
            SELECT COALESCE(AVG(a.workingHours), 0)
            FROM Attendance a
            WHERE a.attendanceDate = :date
            AND a.checkOut IS NOT NULL
            """)
    Double calculateAverageWorkingHours(
            @Param("date") LocalDate date
    );
}