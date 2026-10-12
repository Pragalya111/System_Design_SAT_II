package com.rideshare.hld.controller;

import com.rideshare.hld.model.ArchitectureInfo;
import com.rideshare.hld.model.DispatchResult;
import com.rideshare.hld.service.DispatchService;
import com.rideshare.lld.dto.RideRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for High-Level Design (HLD) End-to-End simulation and architecture introspection.
 * Base Path: /api/hld
 */
@RestController
@RequestMapping("/api/hld")
public class DispatchController {

    private final DispatchService dispatchService;

    public DispatchController(DispatchService dispatchService) {
        this.dispatchService = dispatchService;
    }

    /**
     * Simulates the complete end-to-end dispatch workflow across all logical components:
     * Ride Service -> Location Service -> Fare Calculation Service -> Dispatch Service -> Notification Service.
     *
     * HTTP POST /api/hld/dispatch
     * Status: 201 CREATED
     */
    @PostMapping("/dispatch")
    public ResponseEntity<DispatchResult> simulateDispatch(@Valid @RequestBody RideRequest request) {
        DispatchResult result = dispatchService.dispatchRide(request);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    /**
     * Returns an architectural blueprint detailing logical services, their responsibilities,
     * interaction protocols, and future scalability roadmap (Load Balancer, Redis, Kafka, PostgreSQL).
     *
     * HTTP GET /api/hld/architecture
     * Status: 200 OK
     */
    @GetMapping("/architecture")
    public ResponseEntity<ArchitectureInfo> getArchitecture() {
        ArchitectureInfo architecture = dispatchService.getArchitectureDetails();
        return ResponseEntity.ok(architecture);
    }
}
