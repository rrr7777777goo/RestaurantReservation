package com.restaurantreservation.domain.reservation;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Builder
@Entity(name = "reservation")
@Getter
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
    @Enumerated(EnumType.STRING)
    private ApproveStatus approvestatus;

    // 예약 시간 내 식당 방문 여부
    @Enumerated(EnumType.STRING)
    private VisitStatus visitstatus;


    public void approveReservation() {
        if(this.approvestatus != ApproveStatus.RESERVATRION_REQUEST) {
            throw new RuntimeException("이미 승인이나 거부 처리가 완료된 예약은 다시 승인 할 수 없습니다.");
        }
        this.approvestatus = ApproveStatus.RESERVATION_APPROVE;
    }

    public void rejectReservation() {
        if(this.approvestatus != ApproveStatus.RESERVATRION_REQUEST) {
            throw new RuntimeException("이미 승인이나 거부 처리가 완료된 예약은 다시 승인 할 수 없습니다.");
        }
        this.approvestatus = ApproveStatus.RESERVATION_REJECT;
    }

    public void visitReservation() {
        switch(this.approvestatus) {
            case RESERVATRION_REQUEST:
                throw new RuntimeException("현재 예약 승인이 되지 않아서 방문 처리가 불가능합니다.");
            case RESERVATION_REJECT:
                throw new RuntimeException("현재 예약은 거부 처리가 된 상태입니다.");
        }
        switch(this.visitstatus) {
            case VISIT:
                throw new RuntimeException("이미 방문처리가 된 상태입니다.");
        }

        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime checkTimeStart = reservationtime.minusHours(1);
        LocalDateTime checkTimeEnd = reservationtime.minusMinutes(10);
        if(currentTime.isBefore(checkTimeStart)) {
            throw new RuntimeException("방문 처리가 불가능합니다.(방문 처리는 예약시간 1시간 전부터 가능합니다.)");
        }
        if(currentTime.isAfter(checkTimeEnd)) {
            throw new RuntimeException("방문 처리가 불가능합니다.(방문 처리는 예약시간 10분전까지 완료되어야 합니다.)");
        }

        this.visitstatus = VisitStatus.VISIT;
    }
}
