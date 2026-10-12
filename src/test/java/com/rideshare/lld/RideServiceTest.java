package com.rideshare.lld;

import com.rideshare.common.BadRequestException;
import com.rideshare.lld.dto.DriverResponse;
import com.rideshare.lld.dto.RideRequest;
import com.rideshare.lld.dto.RideResponse;
import com.rideshare.lld.model.Driver;
import com.rideshare.lld.model.RideStatus;
import com.rideshare.lld.repository.DriverRepository;
import com.rideshare.lld.service.RideService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class RideServiceTest {

    @Autowired
    private RideService rideService;

    @Autowired
    private DriverRepository driverRepository;

    private Long testDriverId;

    @BeforeEach
    void setUp() {
        Driver driver = new Driver("Test Driver", "+91-9000000001", 12.9716, 77.5946, true);
        Driver saved = driverRepository.save(driver);
        testDriverId = saved.getId();
    }

    @Test
    void testRequestRide_Success() {
        RideRequest request = new RideRequest(
                "John Doe",
                12.9716, 77.5946,  // Bangalore City Center
                12.9352, 77.6245   // Koramangala
        );

        RideResponse response = rideService.requestRide(request);

        assertNotNull(response);
        assertNotNull(response.getId());
        assertEquals("John Doe", response.getPassengerName());
        assertEquals(RideStatus.REQUESTED, response.getStatus());
        assertTrue(response.getDistanceKm() > 0, "Distance should be greater than zero");
        assertTrue(response.getEstimatedFare() > 50.0, "Fare should include base fare plus distance rate");
    }

    @Test
    void testFindNearbyDrivers_ReturnsSortedAvailableDrivers() {
        List<DriverResponse> nearby = rideService.findNearbyDrivers(12.9716, 77.5946, 20.0);

        assertFalse(nearby.isEmpty(), "Should find available seeded drivers");
        assertTrue(nearby.get(0).getAvailable(), "Found driver must be available");

        // Verify sorting by distance
        for (int i = 0; i < nearby.size() - 1; i++) {
            assertTrue(nearby.get(i).getDistanceKm() <= nearby.get(i + 1).getDistanceKm(),
                    "Drivers should be ordered by proximity ascending");
        }
    }

    @Test
    void testAcceptRide_Success() {
        RideRequest request = new RideRequest(
                "Jane Smith",
                12.9716, 77.5946,
                12.9352, 77.6245
        );
        RideResponse created = rideService.requestRide(request);

        RideResponse accepted = rideService.acceptRide(created.getId(), testDriverId);

        assertEquals(RideStatus.ACCEPTED, accepted.getStatus());
        assertEquals(testDriverId, accepted.getDriverId());

        Driver driver = driverRepository.findById(testDriverId).orElseThrow();
        assertFalse(driver.getAvailable(), "Assigned driver must be marked as not available");
    }

    @Test
    void testCancelRide_ReleasesDriver() {
        RideRequest request = new RideRequest(
                "Alice",
                12.9716, 77.5946,
                12.9352, 77.6245
        );
        RideResponse created = rideService.requestRide(request);
        rideService.acceptRide(created.getId(), testDriverId);

        RideResponse cancelled = rideService.cancelRide(created.getId());

        assertEquals(RideStatus.CANCELLED, cancelled.getStatus());
        Driver driver = driverRepository.findById(testDriverId).orElseThrow();
        assertTrue(driver.getAvailable(), "Driver must be released back to available upon ride cancellation");
    }

    @Test
    void testAcceptRide_AlreadyCancelled_ThrowsException() {
        RideRequest request = new RideRequest(
                "Bob",
                12.9716, 77.5946,
                12.9352, 77.6245
        );
        RideResponse created = rideService.requestRide(request);
        rideService.cancelRide(created.getId());

        assertThrows(BadRequestException.class, () -> {
            rideService.acceptRide(created.getId(), testDriverId);
        }, "Accepting a cancelled ride should throw BadRequestException");
    }
}
