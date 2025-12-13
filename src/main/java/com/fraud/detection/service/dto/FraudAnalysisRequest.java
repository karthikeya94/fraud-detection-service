package com.fraud.detection.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.Instant;

public class FraudAnalysisRequest {
    @NotBlank(message = "Transaction ID is required")
    private String transactionId;

    @NotBlank(message = "Customer ID is required")
    private String customerId;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;

    @NotBlank(message = "Merchant is required")
    private String merchant;

    @NotBlank(message = "Merchant category is required")
    private String merchantCategory;

    @NotNull(message = "Timestamp is required")
    private Instant timestamp;

    @NotNull(message = "Location information is required")
    private LocationInfo location;

    @NotNull(message = "Device information is required")
    private DeviceInfo device;

    public FraudAnalysisRequest() {
    }

    public FraudAnalysisRequest(String transactionId, String customerId, BigDecimal amount, String merchant,
            String merchantCategory, Instant timestamp, LocationInfo location, DeviceInfo device) {
        this.transactionId = transactionId;
        this.customerId = customerId;
        this.amount = amount;
        this.merchant = merchant;
        this.merchantCategory = merchantCategory;
        this.timestamp = timestamp;
        this.location = location;
        this.device = device;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public String getMerchantCategory() {
        return merchantCategory;
    }

    public void setMerchantCategory(String merchantCategory) {
        this.merchantCategory = merchantCategory;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public LocationInfo getLocation() {
        return location;
    }

    public void setLocation(LocationInfo location) {
        this.location = location;
    }

    public DeviceInfo getDevice() {
        return device;
    }

    public void setDevice(DeviceInfo device) {
        this.device = device;
    }

    public static FraudAnalysisRequestBuilder builder() {
        return new FraudAnalysisRequestBuilder();
    }

    public static class FraudAnalysisRequestBuilder {
        private String transactionId;
        private String customerId;
        private BigDecimal amount;
        private String merchant;
        private String merchantCategory;
        private Instant timestamp;
        private LocationInfo location;
        private DeviceInfo device;

        FraudAnalysisRequestBuilder() {
        }

        public FraudAnalysisRequestBuilder transactionId(String transactionId) {
            this.transactionId = transactionId;
            return this;
        }

        public FraudAnalysisRequestBuilder customerId(String customerId) {
            this.customerId = customerId;
            return this;
        }

        public FraudAnalysisRequestBuilder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public FraudAnalysisRequestBuilder merchant(String merchant) {
            this.merchant = merchant;
            return this;
        }

        public FraudAnalysisRequestBuilder merchantCategory(String merchantCategory) {
            this.merchantCategory = merchantCategory;
            return this;
        }

        public FraudAnalysisRequestBuilder timestamp(Instant timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public FraudAnalysisRequestBuilder location(LocationInfo location) {
            this.location = location;
            return this;
        }

        public FraudAnalysisRequestBuilder device(DeviceInfo device) {
            this.device = device;
            return this;
        }

        public FraudAnalysisRequest build() {
            return new FraudAnalysisRequest(transactionId, customerId, amount, merchant, merchantCategory, timestamp,
                    location, device);
        }
    }

    public static class LocationInfo {
        @NotBlank(message = "Country is required")
        private String country;

        @NotBlank(message = "City is required")
        private String city;

        @NotBlank(message = "IP address is required")
        private String ip;

        public LocationInfo() {
        }

        public LocationInfo(String country, String city, String ip) {
            this.country = country;
            this.city = city;
            this.ip = ip;
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

        public static LocationInfoBuilder builder() {
            return new LocationInfoBuilder();
        }

        public static class LocationInfoBuilder {
            private String country;
            private String city;
            private String ip;

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

            public LocationInfo build() {
                return new LocationInfo(country, city, ip);
            }
        }
    }

    public static class DeviceInfo {
        @NotBlank(message = "Device ID is required")
        private String deviceId;

        @NotBlank(message = "Device type is required")
        private String type;

        @NotNull(message = "isNewDevice flag is required")
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
}