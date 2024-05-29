package com.restaurantreservation.controller;

import com.restaurantreservation.domain.restaurant.ForRegisterRestaurant;
import com.restaurantreservation.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public ResponseEntity<?> getRestaurantOrderByNameForKeyword(Pageable pageable, @RequestParam(required = false) String keyword) {
        var result = this.restaurantService.getOrderByName(pageable, keyword);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getOrderByReviewScore")
    public ResponseEntity<?> getRestaurantOrderByReviewScore(Pageable pageable, @RequestParam(required = false) String keyword) {
        var result = this.restaurantService.getOrderByReviewScore(pageable, keyword);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getOrderByLength")
    public ResponseEntity<?> getRestaurantOrderByLength(Pageable pageable, @RequestParam double lat, @RequestParam double lnt, @RequestParam(required = false) String keyword) {
        var result = this.restaurantService.getOrderByLength(pageable, lat, lnt, keyword);
        return ResponseEntity.ok(result);
    }
}
