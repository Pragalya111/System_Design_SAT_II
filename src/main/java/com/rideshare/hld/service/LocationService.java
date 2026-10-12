package com.rideshare.hld.service;

import com.rideshare.lld.dto.DriverResponse;
import com.rideshare.lld.model.Driver;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * High-Level Design (HLD) Logical Component: Location Service.
 * Responsibility: Geospatial calculations, proximity queries, and nearest driver discovery.
 * Future Cloud Scale: Replaced with Redis Geospatial (GEOADD / GEORADIUS) and PostGIS.
 */
@Service
public class LocationService {

    private static final double EARTH_RADIUS_KM = 6371.0;

    /**
     * Calculates distance in kilometers between two coordinates using the Haversine formula.
     */
    public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = EARTH_RADIUS_KM * c;

        BigDecimal bd = BigDecimal.valueOf(distance).setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    /**
     * Finds the single nearest available driver from a list of candidates.
     */
    public Optional<DriverResponse> findNearestDriver(double pickupLat, double pickupLon, List<Driver> candidates) {
        return candidates.stream()
                .filter(driver -> Boolean.TRUE.equals(driver.getAvailable()))
                .map(driver -> {
                    double dist = calculateDistance(pickupLat, pickupLon, driver.getCurrentLatitude(), driver.getCurrentLongitude());
                    return DriverResponse.fromEntity(driver, dist);
                })
                .min(Comparator.comparingDouble(DriverResponse::getDistanceKm));
    }

    /**
     * Finds all available drivers within radius sorted by distance.
     */
    public List<DriverResponse> findDriversWithinRadius(double pickupLat, double pickupLon, List<Driver> candidates, double radiusKm) {
        return candidates.stream()
                .filter(driver -> Boolean.TRUE.equals(driver.getAvailable()))
                .map(driver -> {
                    double dist = calculateDistance(pickupLat, pickupLon, driver.getCurrentLatitude(), driver.getCurrentLongitude());
                    return DriverResponse.fromEntity(driver, dist);
                })
                .filter(driver -> driver.getDistanceKm() <= radiusKm)
                .sorted(Comparator.comparingDouble(DriverResponse::getDistanceKm))
                .collect(Collectors.toList());
    }
}
