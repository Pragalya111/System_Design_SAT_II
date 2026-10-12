package com.rideshare.lld.service;

import com.rideshare.common.BadRequestException;
import com.rideshare.common.ResourceNotFoundException;
import com.rideshare.lld.dto.DriverResponse;
import com.rideshare.lld.dto.RideRequest;
import com.rideshare.lld.dto.RideResponse;
import com.rideshare.lld.model.Driver;
import com.rideshare.lld.model.Ride;
import com.rideshare.lld.model.RideStatus;
import com.rideshare.lld.repository.DriverRepository;
import com.rideshare.lld.repository.RideRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service managing core ride booking, driver proximity calculation,
 * driver assignment, and lifecycle transitions.
 */
@Service
public class RideService {

    private static final double EARTH_RADIUS_KM = 6371.0;
    private static final double BASE_FARE = 50.0;
    private static final double PER_KM_RATE = 12.0;
    private static final double DEFAULT_RADIUS_KM = 15.0;

    private final RideRepository rideRepository;
    private final DriverRepository driverRepository;

    public RideService(RideRepository rideRepository, DriverRepository driverRepository) {
        this.rideRepository = rideRepository;
        this.driverRepository = driverRepository;
    }

    /**
     * Creates and requests a new ride with estimated distance and fare.
     */
    @Transactional
    public RideResponse requestRide(RideRequest request) {
        double distance = calculateDistance(
                request.getPickupLatitude(), request.getPickupLongitude(),
                request.getDestinationLatitude(), request.getDestinationLongitude()
        );

        double fare = calculateFare(distance);

        Ride ride = new Ride(
                request.getPassengerName(),
                request.getPickupLatitude(),
                request.getPickupLongitude(),
                request.getDestinationLatitude(),
                request.getDestinationLongitude(),
                distance,
                fare,
                RideStatus.REQUESTED
        );

        Ride savedRide = rideRepository.save(ride);
        return RideResponse.fromEntity(savedRide, "Ride requested successfully. Searching for nearby drivers.");
    }

    /**
     * Finds nearby available drivers within a search radius (in km), sorted by distance.
     */
    public List<DriverResponse> findNearbyDrivers(Double latitude, Double longitude, Double radiusKm) {
        double searchRadius = (radiusKm != null && radiusKm > 0) ? radiusKm : DEFAULT_RADIUS_KM;

        List<Driver> availableDrivers = driverRepository.findByAvailableTrue();

        return availableDrivers.stream()
                .map(driver -> {
                    double dist = calculateDistance(latitude, longitude, driver.getCurrentLatitude(), driver.getCurrentLongitude());
                    return DriverResponse.fromEntity(driver, dist);
                })
                .filter(response -> response.getDistanceKm() <= searchRadius)
                .sorted(Comparator.comparingDouble(DriverResponse::getDistanceKm))
                .collect(Collectors.toList());
    }

    /**
     * Accepts a ride and assigns a driver.
     * If driverId is provided, assigns that specific driver if available.
     * If driverId is null, automatically finds and assigns the closest available driver.
     */
    @Transactional
    public RideResponse acceptRide(Long rideId, Long driverId) {
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new ResourceNotFoundException("Ride not found with id: " + rideId));

        if (ride.getStatus() != RideStatus.REQUESTED) {
            throw new BadRequestException("Ride cannot be accepted. Current status is: " + ride.getStatus()
                    + ". Only rides in REQUESTED state can be accepted.");
        }

        Driver assignedDriver;

        if (driverId != null) {
            assignedDriver = driverRepository.findById(driverId)
                    .orElseThrow(() -> new ResourceNotFoundException("Driver not found with id: " + driverId));

            if (!Boolean.TRUE.equals(assignedDriver.getAvailable())) {
                throw new BadRequestException("Driver '" + assignedDriver.getName() + "' (ID: " + driverId + ") is not available.");
            }
        } else {
            // Automatically find the closest available driver
            List<DriverResponse> nearby = findNearbyDrivers(ride.getPickupLatitude(), ride.getPickupLongitude(), DEFAULT_RADIUS_KM);
            if (nearby.isEmpty()) {
                throw new BadRequestException("No available drivers found within " + DEFAULT_RADIUS_KM + " km radius.");
            }
            Long nearestDriverId = nearby.get(0).getId();
            assignedDriver = driverRepository.findById(nearestDriverId)
                    .orElseThrow(() -> new ResourceNotFoundException("Driver not found with id: " + nearestDriverId));
        }

        // Mark driver as occupied and link to ride
        assignedDriver.setAvailable(false);
        driverRepository.save(assignedDriver);

        ride.setDriver(assignedDriver);
        ride.setStatus(RideStatus.ACCEPTED);
        Ride updatedRide = rideRepository.save(ride);

        return RideResponse.fromEntity(updatedRide, "Driver '" + assignedDriver.getName() + "' successfully assigned to the ride.");
    }

    /**
     * Retrieves the current status and details of a ride.
     */
    public RideResponse getRideStatus(Long rideId) {
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new ResourceNotFoundException("Ride not found with id: " + rideId));
        return RideResponse.fromEntity(ride, "Ride status retrieved successfully.");
    }

    /**
     * Cancels a ride and frees the assigned driver if one was already assigned.
     */
    @Transactional
    public RideResponse cancelRide(Long rideId) {
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new ResourceNotFoundException("Ride not found with id: " + rideId));

        if (ride.getStatus() == RideStatus.COMPLETED) {
            throw new BadRequestException("Ride cannot be cancelled because it is already COMPLETED.");
        }

        if (ride.getStatus() == RideStatus.CANCELLED) {
            throw new BadRequestException("Ride is already CANCELLED.");
        }

        // If a driver was assigned, release the driver back to available
        if (ride.getDriver() != null) {
            Driver driver = ride.getDriver();
            driver.setAvailable(true);
            driverRepository.save(driver);
        }

        ride.setStatus(RideStatus.CANCELLED);
        Ride updatedRide = rideRepository.save(ride);

        return RideResponse.fromEntity(updatedRide, "Ride cancelled successfully. Driver released if previously assigned.");
    }

    /**
     * Calculates distance in kilometers between two geo-coordinates using the Haversine formula.
     */
    public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = EARTH_RADIUS_KM * c;

        return round(distance, 2);
    }

    /**
     * Calculates estimated fare: Base Fare ($50) + (Distance * $12/km).
     */
    public double calculateFare(double distanceKm) {
        double total = BASE_FARE + (distanceKm * PER_KM_RATE);
        return round(total, 2);
    }

    private double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
