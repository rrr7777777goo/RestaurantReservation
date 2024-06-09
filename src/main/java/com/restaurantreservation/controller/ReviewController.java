package com.restaurantreservation.controller;

import com.restaurantreservation.domain.review.ForRegisterReview;
import com.restaurantreservation.domain.review.ForRequestReview;
import com.restaurantreservation.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    // 최신순으로 특정 식당의 리뷰 정보들을 보기 위해 사용
    @GetMapping("/get/orderbytime")
    public ResponseEntity<?> getReviewOrderByTime(Pageable pageable, @RequestParam int restaurantid) {
        var result = this.reviewService.getOrderByTime(pageable, restaurantid);
        return ResponseEntity.ok(result);
    }

    // 아이디를 기반으로 리뷰를 볼 때 사용
    @GetMapping("/get/fromid")
    @PreAuthorize("hasRole('USER') or hasRole('OWNER')")
    public ResponseEntity<?> getReviewFromId(@RequestParam int id) {
        var result = this.reviewService.getFromid(id);
        return ResponseEntity.ok(result);
    }

    // 새로 리뷰를 등록할 때 사용
    @PostMapping("/add")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> addRestaurant(@RequestBody ForRegisterReview request) {
        var result = this.reviewService.register(request);
        return ResponseEntity.ok(result);
    }

    // 기존의 리뷰를 수정할 때 사용
    @PutMapping("/update")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updateRestaurant(@RequestBody ForRequestReview request) {
        var result = this.reviewService.update(request);
        return ResponseEntity.ok(result);
    }

    // 기존의 리뷰를 삭제할 때 사용
    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('USER') or hasRole('OWNER')")
    public ResponseEntity<?> deleteRestaurant(@RequestBody ForRequestReview request) {
        var result = this.reviewService.delete(request);
        return ResponseEntity.ok(result);
    }
}
