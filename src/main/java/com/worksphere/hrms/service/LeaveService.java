package com.worksphere.hrms.service;

import com.worksphere.hrms.dto.request.LeaveRequest;
import com.worksphere.hrms.dto.response.LeaveResponse;

import java.util.List;

public interface LeaveService {

    LeaveResponse applyLeave(LeaveRequest request);

    List<LeaveResponse> getEmployeeLeaves(Long employeeId);

    LeaveResponse approveLeave(Long leaveId);

    LeaveResponse rejectLeave(Long leaveId);

    List<LeaveResponse> getAllLeaves();

}