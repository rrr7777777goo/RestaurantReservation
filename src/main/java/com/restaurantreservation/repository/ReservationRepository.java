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
    @Query("select new com.restaurantreservation.domain.reservation.Reservation(a.id, a.userid, a.restaurantid, a.reservationtime, a.phonenumber, a.approvestatus, a.visitstatus) \n" +
            "from com.restaurantreservation.domain.reservation.Reservation as a,\n" +
            " com.restaurantreservation.domain.restaurant.Restaurant as b\n" +
            "where a.id = ?1 and a.restaurantid = b.id and b.ownerid = ?2")
    Optional<Reservation> findReservationForApproveOrDeny(int id, int ownerid);

    @Query("select new com.restaurantreservation.domain.reservation.Reservation(a.id, a.userid, a.restaurantid, a.reservationtime, a.phonenumber, a.approvestatus, a.visitstatus) \n" +
            "from com.restaurantreservation.domain.reservation.Reservation as a \n" +
            "where a.id = ?1 and a.userid = ?2")
    Optional<Reservation> findReservationForVisitCheck(int id, int userid);

    @Query("select a.id as id, a.userid as userid, a.restaurantid as restaurantid, a.reservationtime as reservationtime, a.phonenumber as phonenumber, a.approvestatus as approvestatus, a.visitstatus as visitstatus, b.name as restaurantname \n" +
            "from com.restaurantreservation.domain.reservation.Reservation as a,\n" +
            " com.restaurantreservation.domain.restaurant.Restaurant as b\n" +
            "where (a.reservationtime between ?1 and ?2) and a.restaurantid = b.id and b.ownerid = ?3\n" +
            "order by a.reservationtime asc")
    Page<ReservationInformationInterface> findReservationForReservationDateAndOwnerId(Pageable pageable, LocalDateTime startDate, LocalDateTime endDate, int ownerid);
}
