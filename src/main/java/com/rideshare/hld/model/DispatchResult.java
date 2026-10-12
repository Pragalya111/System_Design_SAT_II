package com.rideshare.hld.model;

import com.rideshare.lld.dto.DriverResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Result model capturing the complete outcome of the HLD dispatch workflow,
 * including assigned driver, fare details, and the step-by-step service execution timeline.
 */
public class DispatchResult {

    private String dispatchId;
    private Long rideId;
    private String passengerName;
    private Double pickupLatitude;
    private Double pickupLongitude;
    private Double destinationLatitude;
    private Double destinationLongitude;
    private Double distanceKm;
    private Map<String, Object> fareBreakdown;
    private DriverResponse assignedDriver;
    private String status;
    private LocalDateTime dispatchedAt;
    private List<String> serviceExecutionTimeline;
    private List<String> notificationsSent;

    public DispatchResult() {
    }

    public DispatchResult(String dispatchId, Long rideId, String passengerName,
                          Double pickupLatitude, Double pickupLongitude,
                          Double destinationLatitude, Double destinationLongitude,
                          Double distanceKm, Map<String, Object> fareBreakdown,
                          DriverResponse assignedDriver, String status,
                          LocalDateTime dispatchedAt, List<String> serviceExecutionTimeline,
                          List<String> notificationsSent) {
        this.dispatchId = dispatchId;
        this.rideId = rideId;
        this.passengerName = passengerName;
        this.pickupLatitude = pickupLatitude;
        this.pickupLongitude = pickupLongitude;
        this.destinationLatitude = destinationLatitude;
        this.destinationLongitude = destinationLongitude;
        this.distanceKm = distanceKm;
        this.fareBreakdown = fareBreakdown;
        this.assignedDriver = assignedDriver;
        this.status = status;
        this.dispatchedAt = dispatchedAt;
        this.serviceExecutionTimeline = serviceExecutionTimeline;
        this.notificationsSent = notificationsSent;
    }

    public String getDispatchId() {
        return dispatchId;
    }

    public void setDispatchId(String dispatchId) {
        this.dispatchId = dispatchId;
    }

    public Long getRideId() {
        return rideId;
    }

    public void setRideId(Long rideId) {
        this.rideId = rideId;
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

    public Map<String, Object> getFareBreakdown() {
        return fareBreakdown;
    }

    public void setFareBreakdown(Map<String, Object> fareBreakdown) {
        this.fareBreakdown = fareBreakdown;
    }

    public DriverResponse getAssignedDriver() {
        return assignedDriver;
    }

    public void setAssignedDriver(DriverResponse assignedDriver) {
        this.assignedDriver = assignedDriver;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDispatchedAt() {
        return dispatchedAt;
    }

    public void setDispatchedAt(LocalDateTime dispatchedAt) {
        this.dispatchedAt = dispatchedAt;
    }

    public List<String> getServiceExecutionTimeline() {
        return serviceExecutionTimeline;
    }

    public void setServiceExecutionTimeline(List<String> serviceExecutionTimeline) {
        this.serviceExecutionTimeline = serviceExecutionTimeline;
    }

    public List<String> getNotificationsSent() {
        return notificationsSent;
    }

    public void setNotificationsSent(List<String> notificationsSent) {
        this.notificationsSent = notificationsSent;
    }
}
