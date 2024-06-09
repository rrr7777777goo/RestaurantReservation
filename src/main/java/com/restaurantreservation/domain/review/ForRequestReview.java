package com.restaurantreservation.domain.review;

import lombok.Data;

// 리뷰를 수정하거나 제거할 때 사용하는 클래스 (삭제할 때는 reviewid만 있어도 된다.)
@Data
public class ForRequestReview {
    private int reviewid;
    // 리뷰 점수
    private int score;
    // 리뷰 내용
    private String description;
}
