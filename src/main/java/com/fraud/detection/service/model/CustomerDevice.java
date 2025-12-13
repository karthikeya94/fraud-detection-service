package com.fraud.detection.service.model;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.Instant;
import java.util.List;

public class CustomerDevice {
    private String deviceId;
    private Instant firstUsed;
    private List<String> locations;

    public CustomerDevice() {
    }

    public CustomerDevice(String deviceId, Instant firstUsed, List<String> locations) {
        this.deviceId = deviceId;
        this.firstUsed = firstUsed;
        this.locations = locations;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Instant getFirstUsed() {
        return firstUsed;
    }

    public void setFirstUsed(Instant firstUsed) {
        this.firstUsed = firstUsed;
    }

    public List<String> getLocations() {
        return locations;
    }

    public void setLocations(List<String> locations) {
        this.locations = locations;
    }

    public static CustomerDeviceBuilder builder() {
        return new CustomerDeviceBuilder();
    }

    public static class CustomerDeviceBuilder {
        private String deviceId;
        private Instant firstUsed;
        private List<String> locations;

        CustomerDeviceBuilder() {
        }

        public CustomerDeviceBuilder deviceId(String deviceId) {
            this.deviceId = deviceId;
            return this;
        }

        public CustomerDeviceBuilder firstUsed(Instant firstUsed) {
            this.firstUsed = firstUsed;
            return this;
        }

        public CustomerDeviceBuilder locations(List<String> locations) {
            this.locations = locations;
            return this;
        }

        public CustomerDevice build() {
            return new CustomerDevice(deviceId, firstUsed, locations);
        }
    }
}