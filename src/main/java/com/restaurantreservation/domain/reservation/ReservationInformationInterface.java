package com.restaurantreservation.domain.reservation;

import jakarta.persistence.*;

import java.time.LocalDateTime;

public interface ReservationInformationInterface {
    int getId();
    int getUserid();
    int getRestaurantid();
    LocalDateTime getReservationtime();
    String getPhonenumber();
    @Enumerated(EnumType.STRING)
    ApproveStatus getApprovestatus();
    @Enumerated(EnumType.STRING)
    VisitStatus getVisitstatus();

    String getRestaurantname();
}
