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

    // 이름순으로 정렬된 식당 정보들을 출력
    @GetMapping("/get/orderbyname")
    public ResponseEntity<?> getRestaurantOrderByNameForKeyword(Pageable pageable, @RequestParam(required = false) String keyword) {
        var result = this.restaurantService.getOrderByName(pageable, keyword);
        return ResponseEntity.ok(result);
    }

    // 별점순으로 정렬된 식당 정보들을 출력
    @GetMapping("/get/orderbyreviewscore")
    public ResponseEntity<?> getRestaurantOrderByReviewScore(Pageable pageable, @RequestParam(required = false) String keyword) {
        var result = this.restaurantService.getOrderByReviewScore(pageable, keyword);
        return ResponseEntity.ok(result);
    }

    // 현재 위치에서 가까운 순서대로 식당 정보들을 출력 (서로 간의 거리는 위도, 경도를 기반으로 한다.)
    @GetMapping("/get/orderbylength")
    public ResponseEntity<?> getRestaurantOrderByLength(Pageable pageable, @RequestParam double lat, @RequestParam double lnt, @RequestParam(required = false) String keyword) {
        var result = this.restaurantService.getOrderByLength(pageable, lat, lnt, keyword);
        return ResponseEntity.ok(result);
    }

    // 식당 주인이 소유하고 있는 식당들의 정보를 이름순으로 출력
    @GetMapping("/get/orderbyname/owner")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<?> getRestaurantOrderByNameForKeywordForOwner(Pageable pageable, @RequestParam(required = false) String keyword) {
        var result = this.restaurantService.getOrderByNameForOwner(pageable, keyword);
        return ResponseEntity.ok(result);
    }

    // 식당 주인이 소유하고 있는 식당들의 정보를 별점순으로 출력
    @GetMapping("/get/orderbyreviewscore/owner")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<?> getRestaurantOrderByReviewScoreForOwner(Pageable pageable, @RequestParam(required = false) String keyword) {
        var result = this.restaurantService.getOrderByReviewScoreForOwner(pageable, keyword);
        return ResponseEntity.ok(result);
    }

    // 식당 주인이 소유하고 있는 식당들의 정보를 거리순으로 출력 (서로 간의 거리는 위도, 경도를 기반으로 한다.)
    @GetMapping("/get/orderbylength/owner")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<?> getRestaurantOrderByLengthForOwner(Pageable pageable, @RequestParam double lat, @RequestParam double lnt, @RequestParam(required = false) String keyword) {
        var result = this.restaurantService.getOrderByLengthForOwner(pageable, lat, lnt, keyword);
        return ResponseEntity.ok(result);
    }

    // 식당 아이디를 기반으로 식당 정보 출력
    @GetMapping("/get/fromid")
    @PreAuthorize("hasRole('USER') or hasRole('OWNER')")
    public ResponseEntity<?> getRestaurantFromId(@RequestParam int id) {
        var result = this.restaurantService.getFromId(id);
        return ResponseEntity.ok(result);
    }

    // 새로운 식당 추가
    @PostMapping("/add")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<?> addRestaurant(@RequestBody ForRegisterRestaurant request) {
        var result = this.restaurantService.register(request);
        return ResponseEntity.ok(result);
    }

    // 기존의 식당 정보 수정
    @PutMapping("/update")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<?> updateRestaurant(@RequestBody ForRequestRestaurant request) {
        var result = this.restaurantService.update(request);
        return ResponseEntity.ok(result);
    }

    // 식당 제거
    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<?> deleteRestaurant(@RequestBody ForRequestRestaurant request) {
        var result = this.restaurantService.delete(request);
        return ResponseEntity.ok(result);
    }
}
