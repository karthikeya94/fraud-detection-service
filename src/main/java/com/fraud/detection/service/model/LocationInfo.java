package com.fraud.detection.service.model;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

public class LocationInfo {
    private String country;
    private String city;
    private String ip;
    private Double latitude;
    private Double longitude;

    public LocationInfo() {
    }

    public LocationInfo(String country, String city, String ip, Double latitude, Double longitude) {
        this.country = country;
        this.city = city;
        this.ip = ip;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
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

    public static LocationInfoBuilder builder() {
        return new LocationInfoBuilder();
    }

    public static class LocationInfoBuilder {
        private String country;
        private String city;
        private String ip;
        private Double latitude;
        private Double longitude;

        LocationInfoBuilder() {
        }

        public LocationInfoBuilder country(String country) {
            this.country = country;
            return this;
        }

        public LocationInfoBuilder city(String city) {
            this.city = city;
            return this;
        }

        public LocationInfoBuilder ip(String ip) {
            this.ip = ip;
            return this;
        }

        public LocationInfoBuilder latitude(Double latitude) {
            this.latitude = latitude;
            return this;
        }

        public LocationInfoBuilder longitude(Double longitude) {
            this.longitude = longitude;
            return this;
        }

        public LocationInfo build() {
            return new LocationInfo(country, city, ip, latitude, longitude);
        }
    }
}