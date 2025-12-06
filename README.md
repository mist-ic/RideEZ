# RideEZ Backend

RideEZ is a Spring Boot based ride-sharing backend application that facilitates ride requests between passengers and drivers.
Built as a SprintBoot Project - Praveen Kumar - 24BCS10048

## 🚀 Features

- **User & Driver Authentication**: Secure registration and login using JWT.
- **Ride Management**:
    - Passengers can request rides.
    - Drivers can view pending requests and accept them.
    - Drivers (or Users) can complete rides.
    - Users can view their ride history.
- **Data Persistence**: MongoDB is used to store Users and Rides.
- **Validation**: Input validation for all API endpoints.
- **Exception Handling**: Global exception handling for consistent error responses.

## 🛠 Tech Stack

- **Java 17**
- **Spring Boot 3.2.1**
- **Spring Data MongoDB**
- **Spring Security** (Authentication/Authorization)
- **JWT (JSON Web Token)**
- **Lombok**
- **Maven**

## 📂 Project Structure

```
src/main/java/org/example/rideshare/
├── config/         # Security & JWT specific config
├── controller/     # REST Controllers
├── dto/            # Data Transfer Objects
├── exception/      # Global Exception Handling
├── model/          # MongoDB Entities (User, Ride)
├── repository/     # Data Access Layer
├── service/        # Business Logic
└── util/           # Utility classes (JwtUtil)
```

## 🔧 How to Run

1. **Prerequisites**:
    - Java 17+ installed.
    - Maven installed.
    - MongoDB running locally on port `27017`.

2. **Build & Run**:
    ```bash
    mvn clean install
    mvn spring-boot:run
    ```

## 🔌 API Endpoints

### Authentication

| Method | Endpoint | Description | Auth Required |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/auth/register` | Register a new user (`ROLE_USER` or `ROLE_DRIVER`) | Public |
| `POST` | `/api/auth/login` | Login and receive JWT token | Public |

### Rides

| Method | Endpoint | Description | Auth Required |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/v1/rides` | Request a new ride | `ROLE_USER` |
| `GET` | `/api/v1/user/rides` | View ride history for logged-in user | `ROLE_USER` |
| `GET` | `/api/v1/driver/rides/requests` | View all `REQUESTED` rides | `ROLE_DRIVER` |
| `POST` | `/api/v1/driver/rides/{id}/accept` | Accept a ride request | `ROLE_DRIVER` |
| `POST` | `/api/v1/rides/{id}/complete` | Mark a ride as `COMPLETED` | Authenticated |

## 🧪 Testing with CURL

**1. Register a User**
```bash
curl -X POST http://localhost:8081/api/auth/register \
-H "Content-Type: application/json" \
-d "{\"username\":\"Praveen\",\"password\":\"1234\",\"role\":\"ROLE_USER\"}"
```

**2. Register a Driver**
```bash
curl -X POST http://localhost:8081/api/auth/register \
-H "Content-Type: application/json" \
-d "{\"username\":\"driver1\",\"password\":\"abcd\",\"role\":\"ROLE_DRIVER\"}"
```

**3. Login (Get Token)**
```bash
curl -X POST http://localhost:8081/api/auth/login \
-H "Content-Type: application/json" \
-d "{\"username\":\"Praveen\",\"password\":\"1234\"}"
```

**4. Request a Ride**
```bash
curl -X POST http://localhost:8081/api/v1/rides \
-H "Authorization: Bearer <YOUR_TOKEN_HERE>" \
-H "Content-Type: application/json" \
-d "{\"pickupLocation\":\"Koramangala\",\"dropLocation\":\"Indiranagar\"}"
```