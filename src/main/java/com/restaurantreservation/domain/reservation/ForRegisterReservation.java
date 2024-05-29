package com.restaurantreservation.domain.reservation;

import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class ForRegisterReservation {
    private int restaurantid;
    private String reservationtime;
    private String phonenumber;

    public Reservation toEntity(int userid) {
        return Reservation.builder()
                .userid(userid)
                .restaurantid(this.restaurantid)
                .reservationtime(LocalDateTime.parse(this.reservationtime, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")))
                .phonenumber(this.phonenumber)
                .approvestatus(ApproveStatus.RESERVATRION_REQUEST)
                .visitstatus(VisitStatus.NOT_VISIT)
                .build();
    }
}
