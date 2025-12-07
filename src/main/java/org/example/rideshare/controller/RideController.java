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
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Ride> createRide(@Valid @RequestBody RideDto.CreateRideRequest request, @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(rideService.createRide(request, userDetails.getUsername()));
    }

    @GetMapping("/user/rides")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Ride>> getMyRides(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(rideService.getMyRides(userDetails.getUsername()));
    }

    @GetMapping("/driver/rides/requests")
    @PreAuthorize("hasRole('DRIVER')")
    public ResponseEntity<List<Ride>> getAvailableRides(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(rideService.getAvailableRides());
    }

    @PostMapping("/driver/rides/{id}/accept")
    @PreAuthorize("hasRole('DRIVER')")
    public ResponseEntity<Ride> acceptRide(@PathVariable String id, @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(rideService.acceptRide(id, userDetails.getUsername()));
    }

    @PostMapping("/rides/{id}/complete")
    @PreAuthorize("hasAnyRole('USER', 'DRIVER')")
    public ResponseEntity<Ride> completeRide(@PathVariable String id) {
        return ResponseEntity.ok(rideService.completeRide(id));
    }
}
