package com.rideshare.lld.dto;

import com.rideshare.lld.model.Driver;

/**
 * Response DTO representing driver details and proximity to user.
 */
public class DriverResponse {

    private Long id;
    private String name;
    private String phone;
    private Double latitude;
    private Double longitude;
    private Boolean available;
    private Double distanceKm;

    public DriverResponse() {
    }

    public DriverResponse(Long id, String name, String phone, Double latitude, Double longitude, Boolean available, Double distanceKm) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.latitude = latitude;
        this.longitude = longitude;
        this.available = available;
        this.distanceKm = distanceKm;
    }

    public static DriverResponse fromEntity(Driver driver, Double distanceKm) {
        return new DriverResponse(
                driver.getId(),
                driver.getName(),
                driver.getPhone(),
                driver.getCurrentLatitude(),
                driver.getCurrentLongitude(),
                driver.getAvailable(),
                distanceKm
        );
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

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Double getDistanceKm() {
        return distanceKm;
    }

    public void setDistanceKm(Double distanceKm) {
        this.distanceKm = distanceKm;
    }
}
