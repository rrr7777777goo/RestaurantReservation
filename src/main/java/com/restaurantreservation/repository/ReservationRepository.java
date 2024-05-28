package com.restaurantreservation.repository;

import com.restaurantreservation.domain.Reservation;
import com.restaurantreservation.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

}
