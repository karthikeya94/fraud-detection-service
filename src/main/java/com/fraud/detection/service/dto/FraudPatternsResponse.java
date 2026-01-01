package com.fraud.detection.service.dto;

import com.riskplatform.common.entity.FraudPattern;
import java.util.List;

public class FraudPatternsResponse {
    private List<FraudPattern> patterns;
    private String timeWindow;

    public FraudPatternsResponse() {
    }

    public FraudPatternsResponse(List<FraudPattern> patterns, String timeWindow) {
        this.patterns = patterns;
        this.timeWindow = timeWindow;
    }

    public List<FraudPattern> getPatterns() {
        return patterns;
    }

    public void setPatterns(List<FraudPattern> patterns) {
        this.patterns = patterns;
    }

    public String getTimeWindow() {
        return timeWindow;
    }

    public void setTimeWindow(String timeWindow) {
        this.timeWindow = timeWindow;
    }

    public static FraudPatternsResponseBuilder builder() {
        return new FraudPatternsResponseBuilder();
    }

    public static class FraudPatternsResponseBuilder {
        private List<FraudPattern> patterns;
        private String timeWindow;

        FraudPatternsResponseBuilder() {
        }

        public FraudPatternsResponseBuilder patterns(List<FraudPattern> patterns) {
            this.patterns = patterns;
            return this;
        }

        public FraudPatternsResponseBuilder timeWindow(String timeWindow) {
            this.timeWindow = timeWindow;
            return this;
        }

        public FraudPatternsResponse build() {
            return new FraudPatternsResponse(patterns, timeWindow);
        }
    }
}