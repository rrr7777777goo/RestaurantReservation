package com.restaurantreservation.repository;

import com.restaurantreservation.domain.reservation.Reservation;
import com.restaurantreservation.domain.reservation.ReservationInformationInterface;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    // 현재 식당 주인에게 들어온 예약 정보를 날짜 기반으로 가져온다.
    @Query("select a.id as id, a.userid as userid, a.restaurantid as restaurantid, a.reservationtime as reservationtime, a.phonenumber as phonenumber, a.approvestatus as approvestatus, a.visitstatus as visitstatus, c.name as restaurantname, b.id as reviewid \n" +
            "from com.restaurantreservation.domain.reservation.Reservation as a \n" +
            "left join com.restaurantreservation.domain.review.Review as b on a.id = b.reservationid, \n" +
            " com.restaurantreservation.domain.restaurant.Restaurant as c \n " +
            "where (a.reservationtime between ?1 and ?2) and a.restaurantid = c.id and c.ownerid = ?3\n" +
            "order by a.reservationtime asc, a.id asc")
    Page<ReservationInformationInterface> findReservationForReservationDateAndOwnerId(Pageable pageable, LocalDateTime startDate, LocalDateTime endDate, int ownerid);

    // 현재 유저의 예약 정보를 날짜 기반으로 가져온다.
    @Query("select a.id as id, a.userid as userid, a.restaurantid as restaurantid, a.reservationtime as reservationtime, a.phonenumber as phonenumber, a.approvestatus as approvestatus, a.visitstatus as visitstatus, c.name as restaurantname, b.id as reviewid \n" +
            "from com.restaurantreservation.domain.reservation.Reservation as a \n" +
            "left join com.restaurantreservation.domain.review.Review as b on a.id = b.reservationid, \n" +
            " com.restaurantreservation.domain.restaurant.Restaurant as c \n " +
            "where (a.reservationtime between ?1 and ?2) and a.restaurantid = c.id and a.userid = ?3\n" +
            "order by a.reservationtime asc, a.id asc")
    Page<ReservationInformationInterface> findReservationForReservationDateAndUserId(Pageable pageable, LocalDateTime startTime, LocalDateTime endTime, int userid);

    // 아이디를 기반으로 예약 정보를 가져온다. 이 때 예약한 본인이나 예약한 식당의 주인만 해당 정보를 가져올 수 있다.
    @Query("select a.id as id, a.userid as userid, a.restaurantid as restaurantid, a.reservationtime as reservationtime, a.phonenumber as phonenumber, a.approvestatus as approvestatus, a.visitstatus as visitstatus, c.name as restaurantname, b.id as reviewid \n" +
            "from com.restaurantreservation.domain.reservation.Reservation as a \n" +
            "left join com.restaurantreservation.domain.review.Review as b on a.id = b.reservationid, \n" +
            " com.restaurantreservation.domain.restaurant.Restaurant as c \n " +
            "where a.id = ?1 and a.restaurantid = c.id and (c.ownerid = ?2 or a.userid = ?2)")
    Optional<ReservationInformationInterface> getReservationFromIdAndUserId(int id, int userid);

    // 예약정보 승인 또는 거절용으로 사용하는 Query문, 아이디를 기반으로 가져오며 예약한 식당의 주인만 해당 정보를 가져올 수 있다.
    @Query("select new com.restaurantreservation.domain.reservation.Reservation(a.id, a.userid, a.restaurantid, a.reservationtime, a.phonenumber, a.approvestatus, a.visitstatus) \n" +
            "from com.restaurantreservation.domain.reservation.Reservation as a,\n" +
            " com.restaurantreservation.domain.restaurant.Restaurant as b\n" +
            "where a.id = ?1 and a.restaurantid = b.id and b.ownerid = ?2")
    Optional<Reservation> findReservationForApproveOrDeny(int id, int ownerid);

    // 아이디, 예약 신청자의 아이디를 기반으로 정보를 가져온다. (존재여부 확인용)
    Optional<Reservation> findAllByIdAndUserid(int id, int userid);

}
