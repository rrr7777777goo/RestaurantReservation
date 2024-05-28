package com.restaurantreservation.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Entity(name = "reservation")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    // 고유번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // 유저 아이디
    @Column(name = "user_id")
    private int userid;

    // 식당 아이디
    @Column(name = "restaurant_id")
    private int restaurantid;

    // 식당 시간
    private LocalDateTime reservationtime;

    // 식당 연락처
    private String phonenumber;

    // 예약 승인 여부
    private boolean isapproved;

    // 예약 시간 내 식당 방문 여부
    private boolean isvisited;
}
