package com.rideshare.lld.repository;

import com.rideshare.lld.model.Ride;
import com.rideshare.lld.model.RideStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA Repository for Ride persistence.
 */
@Repository
public interface RideRepository extends JpaRepository<Ride, Long> {

    /**
     * Finds rides filtered by status.
     */
    List<Ride> findByStatus(RideStatus status);
}
