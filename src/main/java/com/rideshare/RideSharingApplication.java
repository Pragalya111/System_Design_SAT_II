package com.rideshare;

import com.rideshare.lld.model.Driver;
import com.rideshare.lld.repository.DriverRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

/**
 * Main Spring Boot Application entry point for the Ride-Sharing & Driver Dispatch Platform.
 * Bootstraps sample drivers on startup into the H2 in-memory database.
 */
@SpringBootApplication
public class RideSharingApplication {

    private static final Logger log = LoggerFactory.getLogger(RideSharingApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(RideSharingApplication.class, args);
    }

    /**
     * Seeds initial sample drivers on application startup so endpoints can be tested immediately.
     */
    @Bean
    public CommandLineRunner seedSampleDrivers(DriverRepository driverRepository) {
        return args -> {
            log.info("=================================================================");
            log.info("  Bootstrapping Sample Drivers into H2 In-Memory Database...    ");
            log.info("=================================================================");

            // Seeding sample drivers around city center (Bangalore coordinates: 12.9716, 77.5946)
            List<Driver> initialDrivers = List.of(
                    new Driver("Alex Johnson", "+91-9876543210", 12.9720, 77.5950, true),
                    new Driver("Sarah Connor", "+91-9876543211", 12.9750, 77.5980, true),
                    new Driver("Michael Chang", "+91-9876543212", 12.9800, 77.6050, true),
                    new Driver("Priya Sharma", "+91-9876543213", 12.9650, 77.5850, true),
                    new Driver("David Miller", "+91-9876543214", 12.9500, 77.5700, false) // Initialized as unavailable (busy)
            );

            driverRepository.saveAll(initialDrivers);

            log.info("Successfully seeded {} sample drivers into H2 database.", initialDrivers.size());
            log.info("-----------------------------------------------------------------");
            log.info("Application is ready!");
            log.info("Base URL:               http://localhost:8080");
            log.info("H2 In-Memory Console:   http://localhost:8080/h2-console");
            log.info("  JDBC URL:             jdbc:h2:mem:ridesharedb");
            log.info("  User:                 sa | Password: (blank)");
            log.info("-----------------------------------------------------------------");
            log.info("Round 2 (LLD) APIs:");
            log.info("  POST http://localhost:8080/api/lld/rides");
            log.info("  GET  http://localhost:8080/api/lld/drivers/nearby?latitude=12.9716&longitude=77.5946");
            log.info("  POST http://localhost:8080/api/lld/rides/{id}/accept?driverId={driverId}");
            log.info("  GET  http://localhost:8080/api/lld/rides/{id}");
            log.info("  PUT  http://localhost:8080/api/lld/rides/{id}/cancel");
            log.info("-----------------------------------------------------------------");
            log.info("Round 3 (HLD) APIs:");
            log.info("  POST http://localhost:8080/api/hld/dispatch");
            log.info("  GET  http://localhost:8080/api/hld/architecture");
            log.info("=================================================================");
        };
    }
}
