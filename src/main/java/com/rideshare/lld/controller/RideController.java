package com.rideshare.lld.controller;

import com.rideshare.lld.dto.DriverResponse;
import com.rideshare.lld.dto.RideRequest;
import com.rideshare.lld.dto.RideResponse;
import com.rideshare.lld.service.RideService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Low-Level Design (LLD) Ride-Booking operations.
 * Base Path: /api/lld
 */
@RestController
@RequestMapping("/api/lld")
public class RideController {

    private final RideService rideService;

    public RideController(RideService rideService) {
        this.rideService = rideService;
    }

    /**
     * 1. Request a ride using pickup and destination coordinates.
     * HTTP POST /api/lld/rides
     * Status: 201 CREATED
     */
    @PostMapping("/rides")
    public ResponseEntity<RideResponse> requestRide(@Valid @RequestBody RideRequest request) {
        RideResponse response = rideService.requestRide(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * 2. Find nearby available drivers using distance calculation.
     * HTTP GET /api/lld/drivers/nearby?latitude=...&longitude=...&radius=...
     * Status: 200 OK
     */
    @GetMapping("/drivers/nearby")
    public ResponseEntity<List<DriverResponse>> findNearbyDrivers(
            @RequestParam("latitude") Double latitude,
            @RequestParam("longitude") Double longitude,
            @RequestParam(value = "radius", required = false, defaultValue = "15.0") Double radius) {
        List<DriverResponse> nearbyDrivers = rideService.findNearbyDrivers(latitude, longitude, radius);
        return ResponseEntity.ok(nearbyDrivers);
    }

    /**
     * 3. Accept or assign an available driver to a ride.
     * HTTP POST /api/lld/rides/{id}/accept?driverId=... (driverId optional; auto-selects nearest if omitted)
     * Status: 200 OK
     */
    @PostMapping("/rides/{id}/accept")
    public ResponseEntity<RideResponse> acceptRide(
            @PathVariable("id") Long rideId,
            @RequestParam(value = "driverId", required = false) Long driverId) {
        RideResponse response = rideService.acceptRide(rideId, driverId);
        return ResponseEntity.ok(response);
    }

    /**
     * 4. Retrieve current ride status by ID.
     * HTTP GET /api/lld/rides/{id}
     * Status: 200 OK
     */
    @GetMapping("/rides/{id}")
    public ResponseEntity<RideResponse> getRideStatus(@PathVariable("id") Long rideId) {
        RideResponse response = rideService.getRideStatus(rideId);
        return ResponseEntity.ok(response);
    }

    /**
     * 5. Cancel a ride by ID.
     * HTTP PUT /api/lld/rides/{id}/cancel
     * Status: 200 OK
     */
    @PutMapping("/rides/{id}/cancel")
    public ResponseEntity<RideResponse> cancelRide(@PathVariable("id") Long rideId) {
        RideResponse response = rideService.cancelRide(rideId);
        return ResponseEntity.ok(response);
    }
}
