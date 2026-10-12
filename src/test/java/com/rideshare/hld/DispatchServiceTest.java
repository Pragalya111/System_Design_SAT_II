package com.rideshare.hld;

import com.rideshare.hld.model.ArchitectureInfo;
import com.rideshare.hld.model.DispatchResult;
import com.rideshare.hld.service.DispatchService;
import com.rideshare.lld.dto.RideRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class DispatchServiceTest {

    @Autowired
    private DispatchService dispatchService;

    @Test
    void testDispatchRide_EndToEndWorkflow() {
        RideRequest request = new RideRequest(
                "Professor Evaluator",
                12.9716, 77.5946,
                12.9352, 77.6245
        );

        DispatchResult result = dispatchService.dispatchRide(request);

        assertNotNull(result);
        assertNotNull(result.getDispatchId());
        assertNotNull(result.getRideId());
        assertEquals("Professor Evaluator", result.getPassengerName());
        assertEquals("DISPATCHED_AND_ACCEPTED", result.getStatus());
        assertNotNull(result.getAssignedDriver());
        assertNotNull(result.getFareBreakdown());

        // Verify multi-service execution timeline
        assertNotNull(result.getServiceExecutionTimeline());
        assertTrue(result.getServiceExecutionTimeline().size() >= 5, "Timeline should record all service interactions");

        // Verify notifications sent to passenger and driver
        assertNotNull(result.getNotificationsSent());
        assertEquals(2, result.getNotificationsSent().size());
    }

    @Test
    void testGetArchitectureDetails() {
        ArchitectureInfo architecture = dispatchService.getArchitectureDetails();

        assertNotNull(architecture);
        assertEquals(5, architecture.getLogicalServices().size());
        assertEquals(6, architecture.getDispatchWorkflowSteps().size());
        assertTrue(architecture.getFutureScalabilityImprovements().containsKey("3_Redis_Location_Caching"));
        assertTrue(architecture.getFutureScalabilityImprovements().containsKey("4_Kafka_Message_Broker"));
    }
}
