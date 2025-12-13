package com.fraud.detection.service.model;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

public class FraudFlag {
    private String flag;
    private String severity;
    private String description;

    public FraudFlag() {
    }

    public FraudFlag(String flag, String severity, String description) {
        this.flag = flag;
        this.severity = severity;
        this.description = description;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static FraudFlagBuilder builder() {
        return new FraudFlagBuilder();
    }

    public static class FraudFlagBuilder {
        private String flag;
        private String severity;
        private String description;

        FraudFlagBuilder() {
        }

        public FraudFlagBuilder flag(String flag) {
            this.flag = flag;
            return this;
        }

        public FraudFlagBuilder severity(String severity) {
            this.severity = severity;
            return this;
        }

        public FraudFlagBuilder description(String description) {
            this.description = description;
            return this;
        }

        public FraudFlag build() {
            return new FraudFlag(flag, severity, description);
        }
    }
}