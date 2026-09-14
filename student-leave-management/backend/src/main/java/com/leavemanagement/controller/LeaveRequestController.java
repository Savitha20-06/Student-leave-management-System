package com.leavemanagement.controller;

import com.leavemanagement.model.LeaveRequest;
import com.leavemanagement.service.LeaveRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/leaves")
@CrossOrigin(origins = "*") // allows the plain HTML/JS frontend to call this API
public class LeaveRequestController {

    @Autowired
    private LeaveRequestService leaveRequestService;

    // Apply for a new leave  ->  POST /api/leaves
    @PostMapping
    public ResponseEntity<LeaveRequest> applyLeave(@RequestBody LeaveRequest leaveRequest) {
        LeaveRequest saved = leaveRequestService.applyLeave(leaveRequest);
        return ResponseEntity.ok(saved);
    }

    // Get all leave requests  ->  GET /api/leaves
    @GetMapping
    public ResponseEntity<List<LeaveRequest>> getAllLeaves() {
        return ResponseEntity.ok(leaveRequestService.getAllLeaves());
    }

    // Get a single leave request by id  ->  GET /api/leaves/{id}
    @GetMapping("/{id}")
    public ResponseEntity<LeaveRequest> getLeaveById(@PathVariable Long id) {
        return leaveRequestService.getLeaveById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get all leave requests for a specific student  ->  GET /api/leaves/student/{rollNumber}
    @GetMapping("/student/{rollNumber}")
    public ResponseEntity<List<LeaveRequest>> getLeavesByRollNumber(@PathVariable String rollNumber) {
        return ResponseEntity.ok(leaveRequestService.getLeavesByRollNumber(rollNumber));
    }

    // Approve a leave  ->  PUT /api/leaves/{id}/approve
    @PutMapping("/{id}/approve")
    public ResponseEntity<LeaveRequest> approveLeave(@PathVariable Long id) {
        LeaveRequest updated = leaveRequestService.updateStatus(id, "APPROVED");
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    // Reject a leave  ->  PUT /api/leaves/{id}/reject
    @PutMapping("/{id}/reject")
    public ResponseEntity<LeaveRequest> rejectLeave(@PathVariable Long id) {
        LeaveRequest updated = leaveRequestService.updateStatus(id, "REJECTED");
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    // Generic status update  ->  PUT /api/leaves/{id}/status   body: { "status": "APPROVED" }
    @PutMapping("/{id}/status")
    public ResponseEntity<LeaveRequest> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        LeaveRequest updated = leaveRequestService.updateStatus(id, body.get("status"));
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    // Delete a leave request  ->  DELETE /api/leaves/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLeave(@PathVariable Long id) {
        boolean deleted = leaveRequestService.deleteLeave(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
