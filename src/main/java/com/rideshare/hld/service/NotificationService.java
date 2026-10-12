package com.rideshare.hld.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * High-Level Design (HLD) Logical Component: Notification Service.
 * Responsibility: Sends push notifications, SMS alerts, and updates to passengers and drivers.
 * Future Cloud Scale: Asynchronous consumer listening to Kafka/RabbitMQ events via Firebase / Twilio.
 */
@Service
public class NotificationService {

    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);

    public List<String> dispatchNotifications(String passengerName, String driverName, String driverPhone, Long rideId) {
        List<String> notifications = new ArrayList<>();

        String passengerMessage = String.format(
                "[SMS/Push to %s]: Driver %s (Phone: %s) has accepted your ride #%d and is en route!",
                passengerName, driverName, driverPhone, rideId
        );

        String driverMessage = String.format(
                "[Driver App Push to %s]: You have been assigned Ride #%d for passenger %s. Navigate to pickup location.",
                driverName, rideId, passengerName
        );

        log.info("Dispatch Notification: {}", passengerMessage);
        log.info("Dispatch Notification: {}", driverMessage);

        notifications.add(passengerMessage);
        notifications.add(driverMessage);

        return notifications;
    }
}
