package com.rideshare.lld.model;

import jakarta.persistence.*;

/**
 * Entity representing a driver in the ride-sharing platform.
 */
@Entity
@Table(name = "drivers")
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private Double currentLatitude;

    @Column(nullable = false)
    private Double currentLongitude;

    @Column(nullable = false)
    private Boolean available = true;

    public Driver() {
    }

    public Driver(String name, String phone, Double currentLatitude, Double currentLongitude, Boolean available) {
        this.name = name;
        this.phone = phone;
        this.currentLatitude = currentLatitude;
        this.currentLongitude = currentLongitude;
        this.available = available;
    }

    public Driver(Long id, String name, String phone, Double currentLatitude, Double currentLongitude, Boolean available) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.currentLatitude = currentLatitude;
        this.currentLongitude = currentLongitude;
        this.available = available;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Double getCurrentLatitude() {
        return currentLatitude;
    }

    public void setCurrentLatitude(Double currentLatitude) {
        this.currentLatitude = currentLatitude;
    }

    public Double getCurrentLongitude() {
        return currentLongitude;
    }

    public void setCurrentLongitude(Double currentLongitude) {
        this.currentLongitude = currentLongitude;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}
