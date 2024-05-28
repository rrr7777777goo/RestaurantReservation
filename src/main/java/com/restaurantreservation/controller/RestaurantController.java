package com.restaurantreservation.controller;

import com.restaurantreservation.domain.ForRegisterRestaurant;
import com.restaurantreservation.domain.RestaurantInformationInterface;
import com.restaurantreservation.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/restaurant")
@RequiredArgsConstructor
public class RestaurantController {
    private final RestaurantService restaurantService;
    @PostMapping("/add")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<?> addRestaurant(@RequestBody ForRegisterRestaurant request) {
        var result = this.restaurantService.register(request);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getOrderByName")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getRestaurantOrderByName(Pageable pageable) {
        var result = this.restaurantService.getOrderByName(pageable);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getOrderByReviewScore")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getRestaurantOrderByReviewScore(Pageable pageable) {
        var result = this.restaurantService.getOrderByReviewScore(pageable);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getOrderByLength")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getRestaurantOrderByLength(Pageable pageable, @RequestParam double lat, @RequestParam double lnt) {
        var result = this.restaurantService.getOrderByLength(pageable, lat, lnt);
        return ResponseEntity.ok(result);
    }
}
