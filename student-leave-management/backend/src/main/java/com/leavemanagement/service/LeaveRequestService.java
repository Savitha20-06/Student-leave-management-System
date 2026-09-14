package com.leavemanagement.service;

import com.leavemanagement.model.LeaveRequest;
import com.leavemanagement.repository.LeaveRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class LeaveRequestService {

    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    public LeaveRequest applyLeave(LeaveRequest leaveRequest) {
        leaveRequest.setStatus("PENDING");
        leaveRequest.setAppliedDate(LocalDate.now().toString());
        return leaveRequestRepository.save(leaveRequest);
    }

    public List<LeaveRequest> getAllLeaves() {
        return leaveRequestRepository.findAll();
    }

    public Optional<LeaveRequest> getLeaveById(Long id) {
        return leaveRequestRepository.findById(id);
    }

    public List<LeaveRequest> getLeavesByRollNumber(String rollNumber) {
        return leaveRequestRepository.findByRollNumber(rollNumber);
    }

    public LeaveRequest updateStatus(Long id, String status) {
        Optional<LeaveRequest> optional = leaveRequestRepository.findById(id);
        if (optional.isPresent()) {
            LeaveRequest leave = optional.get();
            leave.setStatus(status);
            return leaveRequestRepository.save(leave);
        }
        return null;
    }

    public boolean deleteLeave(Long id) {
        if (leaveRequestRepository.existsById(id)) {
            leaveRequestRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
