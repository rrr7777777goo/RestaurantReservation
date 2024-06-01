package com.restaurantreservation.domain.reservation;

import jakarta.persistence.*;

import java.time.LocalDateTime;

public interface ReservationInformationInterface {
    Integer getId();
    Integer getUserid();
    Integer getRestaurantid();
    LocalDateTime getReservationtime();
    String getPhonenumber();
    @Enumerated(EnumType.STRING)
    ApproveStatus getApprovestatus();
    @Enumerated(EnumType.STRING)
    VisitStatus getVisitstatus();
    String getRestaurantname();

    // 식당 리뷰 아이디
    Integer getReviewid();
}
