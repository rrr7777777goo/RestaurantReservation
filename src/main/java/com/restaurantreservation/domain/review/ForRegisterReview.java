package com.restaurantreservation.domain.review;

import com.restaurantreservation.domain.reservation.ApproveStatus;
import com.restaurantreservation.domain.reservation.VisitStatus;
import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// 리뷰를 새로 등록할 때 사용하는 함수
@Data
public class ForRegisterReview {
    // 예약 아이디
    private int reservationid;
    // 리뷰 점수
    private int score;
    // 리뷰 내용
    private String description;

    // 해당 정보를 기반으로 Review 객체 반환 (리뷰 작성 시간은 현재 시간으로 저장된다.)
    public Review toEntity() {
        return Review.builder()
                .reservationid(this.reservationid)
                .reviewtime(LocalDateTime.now())
                .score(this.score)
                .description(this.description)
                .build();
    }
}
