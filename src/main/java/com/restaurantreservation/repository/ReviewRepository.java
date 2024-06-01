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
    boolean existsById(int id);

    boolean existsByReservationid(int reservationid);

    Optional<Review> findAllById(int id);

    @Query(value="select a.id as id, a.reviewtime as reviewtime, a.score as score, a.description as description \n" +
            " from com.restaurantreservation.domain.review.Review as a, \n" +
            "com.restaurantreservation.domain.reservation.Reservation as b \n" +
            "where a.reservationid = b.id and b.restaurantid = ?1 \n" +
            "order by a.reviewtime desc, a.id asc")
    Page<ReviewInformationInterface> findAllByRestaurantIdOrderByTime(Pageable pageable, int restaurantid);
}
