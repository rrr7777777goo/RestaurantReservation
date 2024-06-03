package com.restaurantreservation.repository;

import com.restaurantreservation.domain.review.Review;
import com.restaurantreservation.domain.review.ReviewInformationInterface;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    boolean existsByReservationid(int reservationid);

    @Query(value="select a.id as id, a.reviewtime as reviewtime, a.score as score, a.description as description \n" +
            " from com.restaurantreservation.domain.review.Review as a, \n" +
            "com.restaurantreservation.domain.reservation.Reservation as b \n" +
            "where a.reservationid = b.id and b.restaurantid = ?1 \n" +
            "order by a.reviewtime desc, a.id asc")
    Page<ReviewInformationInterface> findAllByRestaurantIdOrderByTime(Pageable pageable, int restaurantid);

    @Query(value="select a.id as id, a.reviewtime as reviewtime, a.score as score, a.description as description \n" +
            " from com.restaurantreservation.domain.review.Review as a, \n" +
            "com.restaurantreservation.domain.reservation.Reservation as b, " +
            "com.restaurantreservation.domain.restaurant.Restaurant as c \n" +
            "where a.id = ?1 and a.reservationid = b.id and b.restaurantid = c.id and (b.userid = ?2 or c.ownerid = ?2)")
    Optional<ReviewInformationInterface> getReviewFromIdAndUserId(int id, int userid);

    @Query(value="select new com.restaurantreservation.domain.review.Review(a.id, a.reservationid, a.reviewtime, a.score, a.description) \n" +
            " from com.restaurantreservation.domain.review.Review as a, \n" +
            "com.restaurantreservation.domain.reservation.Reservation as b \n" +
            "where a.id = ?1 and a.reservationid = b.id and b.userid = ?2")
    Optional<Review> getReviewForUpdateFromIdAndUserId(int id, int userid);

    @Query(value="select new com.restaurantreservation.domain.review.Review(a.id, a.reservationid, a.reviewtime, a.score, a.description) \n" +
            " from com.restaurantreservation.domain.review.Review as a, \n" +
            "com.restaurantreservation.domain.reservation.Reservation as b, " +
            "com.restaurantreservation.domain.restaurant.Restaurant as c \n" +
            "where a.id = ?1 and a.reservationid = b.id and b.restaurantid = c.id and (b.userid = ?2 or c.ownerid = ?2)")
    Optional<Review> getReviewForDeleteFromIdAndUserId(int id, int userid);
}
