package com.restaurantreservation.controller;

import com.restaurantreservation.domain.restaurant.ForRegisterRestaurant;
import com.restaurantreservation.domain.restaurant.ForRequestRestaurant;
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

    @GetMapping("/get/orderbyname")
    public ResponseEntity<?> getRestaurantOrderByNameForKeyword(Pageable pageable, @RequestParam(required = false) String keyword) {
        var result = this.restaurantService.getOrderByName(pageable, keyword);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/get/orderbyreviewscore")
    public ResponseEntity<?> getRestaurantOrderByReviewScore(Pageable pageable, @RequestParam(required = false) String keyword) {
        var result = this.restaurantService.getOrderByReviewScore(pageable, keyword);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/get/orderbylength")
    public ResponseEntity<?> getRestaurantOrderByLength(Pageable pageable, @RequestParam double lat, @RequestParam double lnt, @RequestParam(required = false) String keyword) {
        var result = this.restaurantService.getOrderByLength(pageable, lat, lnt, keyword);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/get/orderbyname/owner")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<?> getRestaurantOrderByNameForKeywordForOwner(Pageable pageable, @RequestParam(required = false) String keyword) {
        var result = this.restaurantService.getOrderByNameForOwner(pageable, keyword);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/get/orderbyreviewscore/owner")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<?> getRestaurantOrderByReviewScoreForOwner(Pageable pageable, @RequestParam(required = false) String keyword) {
        var result = this.restaurantService.getOrderByReviewScoreForOwner(pageable, keyword);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/get/orderbylength/owner")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<?> getRestaurantOrderByLengthForOwner(Pageable pageable, @RequestParam double lat, @RequestParam double lnt, @RequestParam(required = false) String keyword) {
        var result = this.restaurantService.getOrderByLengthForOwner(pageable, lat, lnt, keyword);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/get/fromid")
    @PreAuthorize("hasRole('USER') or hasRole('OWNER')")
    public ResponseEntity<?> getRestaurantFromId(@RequestParam int id) {
        var result = this.restaurantService.getFromId(id);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<?> addRestaurant(@RequestBody ForRegisterRestaurant request) {
        var result = this.restaurantService.register(request);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<?> updateRestaurant(@RequestBody ForRequestRestaurant request) {
        var result = this.restaurantService.update(request);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<?> deleteRestaurant(@RequestBody ForRequestRestaurant request) {
        var result = this.restaurantService.delete(request);
        return ResponseEntity.ok(result);
    }
}
