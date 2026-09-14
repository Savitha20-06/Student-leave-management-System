package com.leavemanagement.repository;

import com.leavemanagement.model.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {

    // Spring Data JPA auto-generates the query from the method name
    List<LeaveRequest> findByRollNumber(String rollNumber);

    List<LeaveRequest> findByStatus(String status);
}
