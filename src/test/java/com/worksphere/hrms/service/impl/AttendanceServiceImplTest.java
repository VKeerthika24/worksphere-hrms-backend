package com.worksphere.hrms.service.impl;

import com.worksphere.hrms.dto.request.AttendanceRequest;
import com.worksphere.hrms.dto.response.AttendanceResponse;
import com.worksphere.hrms.entity.Attendance;
import com.worksphere.hrms.entity.Employee;
import com.worksphere.hrms.enums.AttendanceStatus;
import com.worksphere.hrms.repository.AttendanceRepository;
import com.worksphere.hrms.repository.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class AttendanceServiceImplTest {

    @Mock
    private AttendanceRepository attendanceRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private AttendanceServiceImpl attendanceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCheckInSuccessfully() {

        // Arrange

        AttendanceRequest request = AttendanceRequest.builder()
                .employeeId(1L)
                .build();

        Employee employee = Employee.builder()
                .id(1L)
                .employeeCode("EMP0001")
                .firstName("Keerthika")
                .lastName("V")
                .build();

        Attendance attendance = Attendance.builder()
                .id(1L)
                .attendanceDate(LocalDate.now())
                .employee(employee)
                .status(AttendanceStatus.PRESENT)
                .late(false)
                .overtimeHours(0.0)
                .workingHours(0.0)
                .build();

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        when(attendanceRepository.findByEmployeeIdAndAttendanceDate(
                eq(1L),
                any(LocalDate.class)))
                .thenReturn(Optional.empty());

        when(attendanceRepository.save(any(Attendance.class)))
                .thenReturn(attendance);

        // Act

        AttendanceResponse response =
                attendanceService.checkIn(request);

        // Assert

        Assertions.assertNotNull(response);

        Assertions.assertEquals(
                "EMP0001",
                response.getEmployeeCode());

        Assertions.assertEquals(
                AttendanceStatus.PRESENT,
                response.getStatus());

        verify(attendanceRepository, times(1))
                .save(any(Attendance.class));
    }

    @Test
    void shouldThrowExceptionWhenAlreadyCheckedIn() {

        // Arrange

        AttendanceRequest request = AttendanceRequest.builder()
                .employeeId(1L)
                .build();

        Employee employee = Employee.builder()
                .id(1L)
                .employeeCode("EMP0001")
                .build();

        Attendance attendance = Attendance.builder()
                .id(1L)
                .employee(employee)
                .attendanceDate(LocalDate.now())
                .build();

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        when(attendanceRepository.findByEmployeeIdAndAttendanceDate(
                eq(1L),
                any(LocalDate.class)))
                .thenReturn(Optional.of(attendance));

        // Act & Assert

        IllegalArgumentException exception =
                Assertions.assertThrows(
                        IllegalArgumentException.class,
                        () -> attendanceService.checkIn(request)
                );

        Assertions.assertEquals(
                "Employee has already checked in today",
                exception.getMessage());

        verify(attendanceRepository, never())
                .save(any(Attendance.class));
    }

    @Test
    void shouldCheckOutSuccessfully() {

        // Arrange

        Employee employee = Employee.builder()
                .id(1L)
                .employeeCode("EMP0001")
                .build();

        Attendance attendance = Attendance.builder()
                .id(1L)
                .attendanceDate(LocalDate.now())
                .checkIn(LocalTime.now().minusHours(8))
                .employee(employee)
                .status(AttendanceStatus.PRESENT)
                .late(false)
                .overtimeHours(0.0)
                .workingHours(0.0)
                .build();

        when(attendanceRepository.findByEmployeeIdAndAttendanceDate(
                eq(1L),
                any(LocalDate.class)))
                .thenReturn(Optional.of(attendance));

        when(attendanceRepository.save(any(Attendance.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act

        AttendanceResponse response =
                attendanceService.checkOut(1L);

        // Assert

        Assertions.assertNotNull(response);

        Assertions.assertNotNull(response.getCheckOut());

        Assertions.assertTrue(
                response.getWorkingHours() >= 7.9
        );

        verify(attendanceRepository, times(1))
                .save(any(Attendance.class));
    }

    @Test
    void shouldThrowExceptionWhenAlreadyCheckedOut() {

        // Arrange

        Employee employee = Employee.builder()
                .id(1L)
                .employeeCode("EMP0001")
                .build();

        Attendance attendance = Attendance.builder()
                .id(1L)
                .attendanceDate(LocalDate.now())
                .checkIn(LocalTime.now().minusHours(8))
                .checkOut(LocalTime.now())
                .employee(employee)
                .build();

        when(attendanceRepository.findByEmployeeIdAndAttendanceDate(
                eq(1L),
                any(LocalDate.class)))
                .thenReturn(Optional.of(attendance));

        // Act & Assert

        IllegalArgumentException exception =
                Assertions.assertThrows(
                        IllegalArgumentException.class,
                        () -> attendanceService.checkOut(1L)
                );

        Assertions.assertEquals(
                "Employee has already checked out today",
                exception.getMessage());

        verify(attendanceRepository, never())
                .save(any(Attendance.class));
    }
}