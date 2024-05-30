package com.restaurantreservation.domain.review;

import lombok.Data;

@Data
public class ForRequestReview {
    private int reviewid;
    // 리뷰 점수
    private int score;
    // 리뷰 내용
    private String description;
}
