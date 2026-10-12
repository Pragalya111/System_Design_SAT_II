package com.rideshare.lld.model;

/**
 * Lifecycle states of a ride.
 * Valid transitions:
 * REQUESTED -> ACCEPTED -> IN_PROGRESS -> COMPLETED
 * REQUESTED / ACCEPTED -> CANCELLED
 */
public enum RideStatus {
    REQUESTED,
    ACCEPTED,
    IN_PROGRESS,
    COMPLETED,
    CANCELLED
}
