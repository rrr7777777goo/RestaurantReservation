package com.restaurantreservation.repository;

import com.restaurantreservation.domain.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    boolean existsById(int id);

    boolean existsByReservationid(int reservationid);

    Optional<Review> findAllById(int id);
}
