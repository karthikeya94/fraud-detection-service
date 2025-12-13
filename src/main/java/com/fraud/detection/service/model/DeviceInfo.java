package com.fraud.detection.service.model;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

public class DeviceInfo {
    private String deviceId;
    private String type;
    private Boolean isNewDevice;

    public DeviceInfo() {
    }

    public DeviceInfo(String deviceId, String type, Boolean isNewDevice) {
        this.deviceId = deviceId;
        this.type = type;
        this.isNewDevice = isNewDevice;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getIsNewDevice() {
        return isNewDevice;
    }

    public void setIsNewDevice(Boolean isNewDevice) {
        this.isNewDevice = isNewDevice;
    }

    public static DeviceInfoBuilder builder() {
        return new DeviceInfoBuilder();
    }

    public static class DeviceInfoBuilder {
        private String deviceId;
        private String type;
        private Boolean isNewDevice;

        DeviceInfoBuilder() {
        }

        public DeviceInfoBuilder deviceId(String deviceId) {
            this.deviceId = deviceId;
            return this;
        }

        public DeviceInfoBuilder type(String type) {
            this.type = type;
            return this;
        }

        public DeviceInfoBuilder isNewDevice(Boolean isNewDevice) {
            this.isNewDevice = isNewDevice;
            return this;
        }

        public DeviceInfo build() {
            return new DeviceInfo(deviceId, type, isNewDevice);
        }
    }
}