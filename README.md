# Fraud Detection Service

A real-time fraud detection service that identifies suspicious transactions using three core detection mechanisms: Velocity Fraud, Geographical Fraud, and Behavioral Fraud.

## Features

- **Real-time Fraud Detection**: Processes transactions immediately upon validation
- **Three Core Detection Types**:
  - Velocity Fraud: Detects unusual transaction frequency patterns
  - Geographical Fraud: Identifies impossible travel and high-risk locations
  - Behavioral Fraud: Recognizes unusual merchant and amount patterns
- **Confidence Scoring**: Calculates fraud confidence with weighted algorithms
- **Multi-tier Actions**: AUTO_BLOCK, MANUAL_REVIEW, MONITOR, or ALLOW based on confidence
- **State Management**: Complete fraud alert lifecycle with status transitions
- **Rule Engine**: Dynamic fraud rules that can be updated without service restart
- **Pattern Recognition**: Identifies emerging fraud patterns across the system
- **Event-Driven**: Kafka integration for real-time processing
- **Persistent Storage**: MongoDB for alert storage and retrieval

## Technology Stack

- **Java 21**
- **Spring Boot 3.5.7**
- **MongoDB** for persistent storage
- **Apache Kafka** for event streaming
- **Maven** for dependency management

## System Architecture

The service follows a layered architecture with clear separation of concerns:

```
┌─────────────────┐    ┌──────────────────┐    ┌────────────────────┐
│   Kafka Events  │───▶│  Fraud Detection │───▶│  Action Execution  │
│                 │    │      Engine      │    │                    │
└─────────────────┘    └──────────────────┘    └────────────────────┘
                                │                         │
                                ▼                         ▼
                       ┌──────────────────┐    ┌────────────────────┐
                       │   MongoDB Store  │    │  External Systems  │
                       └──────────────────┘    └────────────────────┘
```

## Fraud Detection Types

### 1. Velocity Fraud
Detects unusual transaction frequency patterns:
- Excessive transactions in short time windows
- Abnormal amount spikes compared to customer history
- Confidence calculated using weighted factors

### 2. Geographical Fraud
Identifies location-based anomalies:
- Impossible travel between distant locations
- Transactions from high-risk countries
- Device/IP location mismatches

### 3. Behavioral Fraud
Recognizes unusual customer behavior:
- Transactions with unfamiliar merchants
- Abnormal amount patterns
- Account takeover indicators

## API Endpoints

### Fraud Analysis
- `POST /api/v1/fraud/analyze` - Analyze a transaction for fraud
- `GET /api/v1/fraud/alerts/{alertId}` - Retrieve fraud alert details
- `PUT /api/v1/fraud/alerts/{alertId}/resolve` - Resolve a fraud alert

### Pattern Detection
- `GET /api/v1/fraud/patterns` - Get detected fraud patterns

### Rule Management
- `POST /api/v1/fraud/rules/add` - Add a new fraud detection rule

## Configuration

The service requires MongoDB and Kafka to be available:
- MongoDB: `mongodb://localhost:27017/fraud_detection`
- Kafka: `localhost:9092`

## Getting Started

1. Ensure MongoDB and Kafka are running
2. Clone the repository
3. Run `mvn clean install`
4. Start the service with `mvn spring-boot:run`

## Contributing

Please read CONTRIBUTING.md for details on our code of conduct and the process for submitting pull requests.

## License

This project is licensed under the MIT License - see the LICENSE.md file for details.