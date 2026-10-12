package com.rideshare.hld.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * High-Level Design (HLD) Logical Component: Fare Calculation Service.
 * Responsibility: Computes dynamic pricing, base rates, distance rates, and surge charges.
 * Future Cloud Scale: Dedicated microservice with real-time ML-based pricing models.
 */
@Service
public class FareCalculationService {

    private static final double BASE_FARE = 50.00;
    private static final double PER_KM_RATE = 12.00;
    private static final double SURGE_MULTIPLIER = 1.00; // Normal demand

    public double calculateTotalFare(double distanceKm) {
        double subtotal = BASE_FARE + (distanceKm * PER_KM_RATE);
        double total = subtotal * SURGE_MULTIPLIER;
        return round(total, 2);
    }

    public Map<String, Object> getDetailedFareBreakdown(double distanceKm) {
        Map<String, Object> breakdown = new LinkedHashMap<>();
        double distanceCost = round(distanceKm * PER_KM_RATE, 2);
        double totalFare = calculateTotalFare(distanceKm);

        breakdown.put("currency", "INR");
        breakdown.put("baseFare", BASE_FARE);
        breakdown.put("distanceKm", distanceKm);
        breakdown.put("ratePerKm", PER_KM_RATE);
        breakdown.put("distanceCost", distanceCost);
        breakdown.put("surgeMultiplier", SURGE_MULTIPLIER);
        breakdown.put("totalEstimatedFare", totalFare);
        return breakdown;
    }

    private double round(double value, int places) {
        BigDecimal bd = BigDecimal.valueOf(value).setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
