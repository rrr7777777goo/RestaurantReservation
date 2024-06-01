package com.restaurantreservation.controller;

import com.restaurantreservation.domain.review.ForRegisterReview;
import com.restaurantreservation.domain.review.ForRequestReview;
import com.restaurantreservation.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    @PostMapping("/add")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> addRestaurant(@RequestBody ForRegisterReview request) {
        var result = this.reviewService.register(request);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updateRestaurant(@RequestBody ForRequestReview request) {
        var result = this.reviewService.update(request);
        return ResponseEntity.ok("");
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('USER') or hasRole('OWNER')")
    public ResponseEntity<?> deleteRestaurant(@RequestBody ForRegisterReview request) {
        return ResponseEntity.ok("");
    }
}
