package com.rideshare.lld.repository;

import com.rideshare.lld.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA Repository for Driver persistence.
 */
@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {

    /**
     * Finds all drivers that are currently available for assignment.
     */
    List<Driver> findByAvailableTrue();
}
