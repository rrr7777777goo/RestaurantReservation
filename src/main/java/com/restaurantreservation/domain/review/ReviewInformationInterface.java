package com.restaurantreservation.domain.review;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

public interface ReviewInformationInterface {
    Integer getId(); // 현재 리뷰 아이디
    LocalDateTime getReviewtime(); // 리뷰 작성 시간
    Integer getScore(); // 리뷰 점수
    String getDescription(); // 리뷰 설명
}
