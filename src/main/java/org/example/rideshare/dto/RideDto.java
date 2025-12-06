package org.example.rideshare.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

public class RideDto {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateRideRequest {
        @NotBlank(message = "Pickup location is required")
        private String pickupLocation;

        @NotBlank(message = "Drop location is required")
        private String dropLocation;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RideResponse {
        private String id;
        private String pickupLocation;
        private String dropLocation;
        private String status;
        private String  driverId;
        private String  userId;
        private Date createdAt;
    }
}
