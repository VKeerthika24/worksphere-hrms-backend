package com.worksphere.hrms.service.impl;

import com.worksphere.hrms.dto.request.LeaveRequest;
import com.worksphere.hrms.dto.response.LeaveResponse;
import com.worksphere.hrms.entity.Employee;
import com.worksphere.hrms.entity.Leave;
import com.worksphere.hrms.enums.LeaveStatus;
import com.worksphere.hrms.exception.ResourceNotFoundException;
import com.worksphere.hrms.mapper.LeaveMapper;
import com.worksphere.hrms.repository.EmployeeRepository;
import com.worksphere.hrms.repository.LeaveRepository;
import com.worksphere.hrms.service.LeaveService;
import com.worksphere.hrms.util.LogMessages;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LeaveServiceImpl implements LeaveService {

    private static final Logger logger =
            LoggerFactory.getLogger(LeaveServiceImpl.class);

    private final LeaveRepository leaveRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public LeaveResponse applyLeave(LeaveRequest request) {

        Employee employee = employeeRepository
                .findById(request.getEmployeeId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee not found"));

        if (request.getEndDate().isBefore(request.getStartDate())) {
            throw new IllegalArgumentException(
                    "End date cannot be before start date");
        }

        Leave leave = LeaveMapper.toEntity(request, employee);

        Leave savedLeave = leaveRepository.save(leave);

        logger.info(
                "{} : {}",
                LogMessages.LEAVE_APPLIED,
                employee.getEmployeeCode()
        );

        return LeaveMapper.toResponse(savedLeave);
    }

    @Override
    public List<LeaveResponse> getEmployeeLeaves(Long employeeId) {

        return leaveRepository
                .findByEmployeeId(employeeId)
                .stream()
                .map(LeaveMapper::toResponse)
                .toList();
    }

    @Override
    public LeaveResponse approveLeave(Long leaveId) {

        Leave leave = leaveRepository
                .findById(leaveId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Leave not found"));

        if (leave.getStatus() != LeaveStatus.PENDING) {
            throw new IllegalArgumentException(
                    "Leave request already processed");
        }

        leave.setStatus(LeaveStatus.APPROVED);

        Leave updatedLeave = leaveRepository.save(leave);

        logger.info(
                "{} : {}",
                LogMessages.LEAVE_APPROVED,
                leave.getEmployee().getEmployeeCode()
        );

        return LeaveMapper.toResponse(updatedLeave);
    }

    @Override
    public LeaveResponse rejectLeave(Long leaveId) {

        Leave leave = leaveRepository
                .findById(leaveId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Leave not found"));

        if (leave.getStatus() != LeaveStatus.PENDING) {
            throw new IllegalArgumentException(
                    "Leave request already processed");
        }

        leave.setStatus(LeaveStatus.REJECTED);

        Leave updatedLeave = leaveRepository.save(leave);

        logger.info(
                "{} : {}",
                LogMessages.LEAVE_REJECTED,
                leave.getEmployee().getEmployeeCode()
        );

        return LeaveMapper.toResponse(updatedLeave);
    }

    @Override
    public List<LeaveResponse> getAllLeaves() {

        return leaveRepository.findAll()
                .stream()
                .map(LeaveMapper::toResponse)
                .toList();
    }
}