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

        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime minimum = currentTime.plusHours(3);
        LocalDateTime maximum = currentTime.plusDays(3).plusHours(3);

        LocalDateTime reservationTime = LocalDateTime.parse(this.reservationtime, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));

        if(reservationTime.isBefore(minimum) || reservationTime.isAfter(maximum)) {
            throw new RuntimeException("예약 신청 불가능합니다. (예약 신청은 현재시간부터 3시간 이후, 75시간 이내로 가능합니다. )");
        }

        return Reservation.builder()
                .userid(userid)
                .restaurantid(this.restaurantid)
                .reservationtime(reservationTime)
                .phonenumber(this.phonenumber)
                .approvestatus(ApproveStatus.RESERVATRION_REQUEST)
                .visitstatus(VisitStatus.NOT_VISIT)
                .build();
    }
}
