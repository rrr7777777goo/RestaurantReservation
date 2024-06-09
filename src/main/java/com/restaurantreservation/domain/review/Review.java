package com.restaurantreservation.domain.review;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Entity(name = "review")
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    // 고유번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // 예약정보 아이디
    @Column(name = "reservation_id")
    private int reservationid;

    // 리뷰 작성 시간
    private LocalDateTime reviewtime;

    // 리뷰 점수
    private int score;

    // 리뷰 내용
    private String description;

    // 리뷰 수정용 함수
    public void updateReview(ForRequestReview forRequestReview) {
        if(this.id == forRequestReview.getReviewid()) {
            this.reviewtime = LocalDateTime.now();
            this.score = forRequestReview.getScore();
            this.description = forRequestReview.getDescription();
        }
    }
}
