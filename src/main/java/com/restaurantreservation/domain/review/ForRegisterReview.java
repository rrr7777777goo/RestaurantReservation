package com.restaurantreservation.domain.review;

import com.restaurantreservation.domain.reservation.ApproveStatus;
import com.restaurantreservation.domain.reservation.VisitStatus;
import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class ForRegisterReview {
    // 예약 아이디
    private int reservationid;
    // 리뷰 점수
    private int score;
    // 리뷰 내용
    private String description;

    public Review toEntity() {
        return Review.builder()
                .reservationid(this.reservationid)
                .reviewtime(LocalDateTime.now())
                .score(this.score)
                .description(this.description)
                .build();
    }
}
