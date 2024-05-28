package com.restaurantreservation.repository;

import com.restaurantreservation.domain.Restaurant;
import com.restaurantreservation.domain.RestaurantInformationInterface;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
    Optional<Restaurant> findAllById(int id);

    @Query(value="select a.name as name, a.address as address, a.description as description, a.lat as lat, a.lnt as lnt, (ifnull(avg(c.score), 0)) as reviewscore\n" +
            "from com.restaurantreservation.domain.Restaurant as a \n" +
            "left join com.restaurantreservation.domain.Reservation as b on a.id = b.restaurantid \n" +
            "left join com.restaurantreservation.domain.Review as c on b.id = c.reservationid\n" +
            "group by a.id\n" +
            "order by a.name asc\n")
    Page<RestaurantInformationInterface> findAllOrderByName(Pageable pageable);

    @Query(value="select a.name as name, a.address as address, a.description as description, a.lat as lat, a.lnt as lnt, (ifnull(avg(c.score), 0)) as reviewscore\n" +
            "from com.restaurantreservation.domain.Restaurant as a \n" +
            "left join com.restaurantreservation.domain.Reservation as b on a.id = b.restaurantid \n" +
            "left join com.restaurantreservation.domain.Review as c on b.id = c.reservationid\n" +
            "group by a.id\n" +
            "order by reviewscore desc\n")
    Page<RestaurantInformationInterface> findAllOrderByReviewScore(Pageable pageable);

    @Query(value="select a.name as name, a.address as address, a.description as description, a.lat as lat, a.lnt as lnt, (ifnull(avg(c.score), 0)) as reviewscore\n" +
            "from com.restaurantreservation.domain.Restaurant as a \n" +
            "left join com.restaurantreservation.domain.Reservation as b on a.id = b.restaurantid \n" +
            "left join com.restaurantreservation.domain.Review as c on b.id = c.reservationid\n" +
            "group by a.id\n" +
            "order by power(lat - ?1, 2) + power(lnt - ?2, 2) asc\n")
    Page<RestaurantInformationInterface> findAllOrderByLength(Pageable pageable, double lat, double lnt);
}
