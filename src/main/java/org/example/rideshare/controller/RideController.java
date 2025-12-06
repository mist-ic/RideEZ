package org.example.rideshare.controller;

import jakarta.validation.Valid;
import org.example.rideshare.dto.RideDto;
import org.example.rideshare.model.Ride;
import org.example.rideshare.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class RideController {

    @Autowired
    private RideService rideService;

    @PostMapping("/rides")
    public ResponseEntity<Ride> createRide(@Valid @RequestBody RideDto.CreateRideRequest request, @AuthenticationPrincipal UserDetails userDetails) {
        // According to requirements: Must be logged in as USER.
        // We can enforce this via SecurityConfig or here.
        // For simplicity, relying on AuthenticationPrincipal.
        // If we need role check: 
        if (userDetails.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_USER"))) {
             return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok(rideService.createRide(request, userDetails.getUsername()));
    }

    @GetMapping("/user/rides")
    public ResponseEntity<List<Ride>> getMyRides(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(rideService.getMyRides(userDetails.getUsername()));
    }

    @GetMapping("/driver/rides/requests")
    public ResponseEntity<List<Ride>> getAvailableRides(@AuthenticationPrincipal UserDetails userDetails) {
        // Check role driver
        if (userDetails.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_DRIVER"))) {
            return ResponseEntity.status(403).build();
       }
        return ResponseEntity.ok(rideService.getAvailableRides());
    }

    @PostMapping("/driver/rides/{id}/accept")
    public ResponseEntity<Ride> acceptRide(@PathVariable String id, @AuthenticationPrincipal UserDetails userDetails) {
         if (userDetails.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_DRIVER"))) {
            return ResponseEntity.status(403).build();
       }
        return ResponseEntity.ok(rideService.acceptRide(id, userDetails.getUsername()));
    }

    @PostMapping("/rides/{id}/complete")
    public ResponseEntity<Ride> completeRide(@PathVariable String id) {
        // User or Driver can complete.
        return ResponseEntity.ok(rideService.completeRide(id));
    }
}
