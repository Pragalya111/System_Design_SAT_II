package com.rideshare.lld.dto;

import com.rideshare.lld.model.Ride;
import com.rideshare.lld.model.RideStatus;

import java.time.LocalDateTime;

/**
 * Response DTO returned to clients for ride operations.
 */
public class RideResponse {

    private Long id;
    private String passengerName;
    private Double pickupLatitude;
    private Double pickupLongitude;
    private Double destinationLatitude;
    private Double destinationLongitude;
    private Double distanceKm;
    private Double estimatedFare;
    private RideStatus status;
    private Long driverId;
    private String driverName;
    private String driverPhone;
    private LocalDateTime requestedAt;
    private LocalDateTime completedAt;
    private String message;

    public RideResponse() {
    }

    public static RideResponse fromEntity(Ride ride, String message) {
        RideResponse response = new RideResponse();
        response.setId(ride.getId());
        response.setPassengerName(ride.getPassengerName());
        response.setPickupLatitude(ride.getPickupLatitude());
        response.setPickupLongitude(ride.getPickupLongitude());
        response.setDestinationLatitude(ride.getDestinationLatitude());
        response.setDestinationLongitude(ride.getDestinationLongitude());
        response.setDistanceKm(ride.getDistanceKm());
        response.setEstimatedFare(ride.getEstimatedFare());
        response.setStatus(ride.getStatus());
        if (ride.getDriver() != null) {
            response.setDriverId(ride.getDriver().getId());
            response.setDriverName(ride.getDriver().getName());
            response.setDriverPhone(ride.getDriver().getPhone());
        }
        response.setRequestedAt(ride.getRequestedAt());
        response.setCompletedAt(ride.getCompletedAt());
        response.setMessage(message);
        return response;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public Double getPickupLatitude() {
        return pickupLatitude;
    }

    public void setPickupLatitude(Double pickupLatitude) {
        this.pickupLatitude = pickupLatitude;
    }

    public Double getPickupLongitude() {
        return pickupLongitude;
    }

    public void setPickupLongitude(Double pickupLongitude) {
        this.pickupLongitude = pickupLongitude;
    }

    public Double getDestinationLatitude() {
        return destinationLatitude;
    }

    public void setDestinationLatitude(Double destinationLatitude) {
        this.destinationLatitude = destinationLatitude;
    }

    public Double getDestinationLongitude() {
        return destinationLongitude;
    }

    public void setDestinationLongitude(Double destinationLongitude) {
        this.destinationLongitude = destinationLongitude;
    }

    public Double getDistanceKm() {
        return distanceKm;
    }

    public void setDistanceKm(Double distanceKm) {
        this.distanceKm = distanceKm;
    }

    public Double getEstimatedFare() {
        return estimatedFare;
    }

    public void setEstimatedFare(Double estimatedFare) {
        this.estimatedFare = estimatedFare;
    }

    public RideStatus getStatus() {
        return status;
    }

    public void setStatus(RideStatus status) {
        this.status = status;
    }

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverPhone() {
        return driverPhone;
    }

    public void setDriverPhone(String driverPhone) {
        this.driverPhone = driverPhone;
    }

    public LocalDateTime getRequestedAt() {
        return requestedAt;
    }

    public void setRequestedAt(LocalDateTime requestedAt) {
        this.requestedAt = requestedAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
