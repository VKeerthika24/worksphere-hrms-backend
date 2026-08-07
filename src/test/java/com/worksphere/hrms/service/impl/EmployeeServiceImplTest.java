package com.worksphere.hrms.service.impl;

import com.worksphere.hrms.exception.ResourceNotFoundException;
import com.worksphere.hrms.repository.DepartmentRepository;
import com.worksphere.hrms.repository.EmployeeRepository;
import com.worksphere.hrms.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.worksphere.hrms.dto.request.EmployeeRequest;
import com.worksphere.hrms.dto.response.EmployeeResponse;
import com.worksphere.hrms.entity.Department;
import com.worksphere.hrms.entity.Employee;
import com.worksphere.hrms.entity.User;
import com.worksphere.hrms.enums.EmployeeStatus;
import com.worksphere.hrms.enums.Gender;
import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.Mockito.*;
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateEmployeeSuccessfully() {

        // Arrange

        EmployeeRequest request = EmployeeRequest.builder()
                .firstName("Keerthika")
                .lastName("V")
                .gender(Gender.FEMALE)
                .dateOfBirth(LocalDate.of(2003,10,20))
                .phoneNumber("9876543210")
                .address("Tirupur")
                .designation("Software Engineer")
                .salary(BigDecimal.valueOf(65000))
                .joiningDate(LocalDate.now())
                .departmentId(1L)
                .userId(1L)
                .build();

        Department department = Department.builder()
                .id(1L)
                .name("IT")
                .build();

        User user = User.builder()
                .id(1L)
                .email("keerthika@gmail.com")
                .build();

        Employee employee = Employee.builder()
                .id(1L)
                .employeeCode("EMP0001")
                .firstName("Keerthika")
                .lastName("V")
                .gender(Gender.FEMALE)
                .dateOfBirth(LocalDate.of(2003,10,20))
                .phoneNumber("9876543210")
                .address("Tirupur")
                .designation("Software Engineer")
                .salary(BigDecimal.valueOf(65000))
                .joiningDate(LocalDate.now())
                .status(EmployeeStatus.ACTIVE)
                .department(department)
                .user(user)
                .build();

        when(departmentRepository.findById(1L))
                .thenReturn(java.util.Optional.of(department));

        when(userRepository.findById(1L))
                .thenReturn(java.util.Optional.of(user));

        when(employeeRepository.save(any(Employee.class)))
                .thenReturn(employee);

        // Act

        EmployeeResponse response =
                employeeService.createEmployee(request);

        // Assert

        Assertions.assertNotNull(response);

        Assertions.assertEquals(
                "Keerthika",
                response.getFirstName());

        Assertions.assertEquals(
                "EMP0001",
                response.getEmployeeCode());

        verify(employeeRepository, times(1))
                .save(any(Employee.class));
    }

    @Test
    void shouldThrowExceptionWhenDepartmentNotFound() {

        // Arrange
        EmployeeRequest request = EmployeeRequest.builder()
                .firstName("Keerthika")
                .lastName("V")
                .gender(Gender.FEMALE)
                .dateOfBirth(LocalDate.of(2003,10,20))
                .phoneNumber("9876543210")
                .address("Tirupur")
                .designation("Software Engineer")
                .salary(BigDecimal.valueOf(65000))
                .joiningDate(LocalDate.now())
                .departmentId(1L)
                .userId(1L)
                .build();

        when(departmentRepository.findById(1L))
                .thenReturn(java.util.Optional.empty());

        // Act & Assert

        ResourceNotFoundException exception =
                Assertions.assertThrows(
                        ResourceNotFoundException.class,
                        () -> employeeService.createEmployee(request)
                );

        Assertions.assertEquals(
                "Department not found",
                exception.getMessage());

        verify(employeeRepository, never())
                .save(any(Employee.class));
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {

        EmployeeRequest request = EmployeeRequest.builder()
                .firstName("Keerthika")
                .lastName("V")
                .gender(Gender.FEMALE)
                .dateOfBirth(LocalDate.of(2003,10,20))
                .phoneNumber("9876543210")
                .address("Tirupur")
                .designation("Software Engineer")
                .salary(BigDecimal.valueOf(65000))
                .joiningDate(LocalDate.now())
                .departmentId(1L)
                .userId(1L)
                .build();

        Department department = Department.builder()
                .id(1L)
                .name("IT")
                .build();

        when(departmentRepository.findById(1L))
                .thenReturn(java.util.Optional.of(department));

        when(userRepository.findById(1L))
                .thenReturn(java.util.Optional.empty());

        ResourceNotFoundException exception =
                Assertions.assertThrows(
                        ResourceNotFoundException.class,
                        () -> employeeService.createEmployee(request)
                );

        Assertions.assertEquals(
                "User not found",
                exception.getMessage());

        verify(employeeRepository, never())
                .save(any(Employee.class));
    }

    @Test
    void shouldGetEmployeeByIdSuccessfully() {

        // Arrange

        Department department = Department.builder()
                .id(1L)
                .name("IT")
                .build();

        User user = User.builder()
                .id(1L)
                .email("keerthika@gmail.com")
                .build();

        Employee employee = Employee.builder()
                .id(1L)
                .employeeCode("EMP0001")
                .firstName("Keerthika")
                .lastName("V")
                .gender(Gender.FEMALE)
                .dateOfBirth(LocalDate.of(2003, 10, 20))
                .phoneNumber("9876543210")
                .address("Tirupur")
                .designation("Software Engineer")
                .salary(BigDecimal.valueOf(65000))
                .joiningDate(LocalDate.now())
                .status(EmployeeStatus.ACTIVE)
                .department(department)
                .user(user)
                .build();

        when(employeeRepository.findById(1L))
                .thenReturn(java.util.Optional.of(employee));

        // Act

        EmployeeResponse response =
                employeeService.getEmployeeById(1L);

        // Assert

        Assertions.assertNotNull(response);

        Assertions.assertEquals(
                "EMP0001",
                response.getEmployeeCode());

        Assertions.assertEquals(
                "Keerthika",
                response.getFirstName());

        verify(employeeRepository, times(1))
                .findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenEmployeeNotFound() {

        when(employeeRepository.findById(100L))
                .thenReturn(java.util.Optional.empty());

        ResourceNotFoundException exception =
                Assertions.assertThrows(
                        ResourceNotFoundException.class,
                        () -> employeeService.getEmployeeById(100L)
                );

        Assertions.assertEquals(
                "Employee not found",
                exception.getMessage());

        verify(employeeRepository, times(1))
                .findById(100L);
    }

    @Test
    void shouldDeleteEmployeeSuccessfully() {

    }

    @Test
    void shouldSearchEmployeesSuccessfully() {

    }
}